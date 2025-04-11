package org.succlz123.deepco.app.mcp

import com.anthropic.core.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.client.Client
import io.modelcontextprotocol.kotlin.sdk.client.StdioClientTransport
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import kotlinx.serialization.json.JsonObject
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.mcp.biz.InputSchema
import org.succlz123.deepco.app.mcp.biz.Tool
import org.succlz123.deepco.app.mcp.biz.ToolUse
import org.succlz123.lib.logger.Logger

class MCPClient : AutoCloseable {

    // Initialize MCP client
    private val mcp: Client = Client(clientInfo = Implementation(name = "mcp-client-cli", version = AppBuildConfig.versionName))

    private fun JsonObject.toJsonValue(): JsonValue {
        val mapper = ObjectMapper()
        val node = mapper.readTree(this.toString())
        return JsonValue.fromJsonNode(node)
    }

    suspend fun connectToServer(command: String?, args: List<String>?): List<Tool> {
        if (command.isNullOrEmpty() || args.isNullOrEmpty()) {
            return emptyList()
        }
        // Command not support
        if (command != "npx" && command != "node" && command != "uv" && command != "python" && command != "python3" && command != "java") {
            return emptyList()
        }
        try {
            // Build the command based on the file extension of the server script
//            val command = buildList {
//                when (serverScriptPath.substringAfterLast(".")) {
//                    "js" -> add("node")
//                    "py" -> add(if (System.getProperty("os.name").lowercase().contains("win")) "python" else "python3")
//                    "jar" -> addAll(listOf("java", "-jar"))
//                    else -> throw IllegalArgumentException("Server script must be a .js, .py or .jar file")
//                }
//                add(serverScriptPath)
//            }

            Logger.log("Connecting to MCP server with command: $command and args: $args")

            // Start the server process
            val process = ProcessBuilder(*(buildList {
                add(command)
                addAll(args)
            }.toTypedArray())).start()

            // Setup I/O transport using the process streams
            val transport = StdioClientTransport(
                input = process.inputStream.asSource().buffered(),
                output = process.outputStream.asSink().buffered()
            )

            // Connect the MCP client to the server using the transport
            mcp.connect(transport)

            // Request the list of available tools from the server
            val toolsResult = mcp.listTools()
            val tools = toolsResult?.tools?.map { tool ->
                Tool(
                    name = tool.name, description = tool.description ?: "",
                    input_schema = InputSchema(
                        type = tool.inputSchema.type,
                        properties = tool.inputSchema.properties,
                        required = tool.inputSchema.required
                    )
                )
            } ?: emptyList()
            Logger.log("Connected to server with tools: ${tools.joinToString(", ") { it.name.orEmpty() }}")
            return tools
        } catch (e: Exception) {
            Logger.log("Failed to connect to MCP server: $e")
            return emptyList()
        }
    }

    suspend fun callServerTool(toolName: String, toolArgs: Map<String, Any?>): ToolUse {
        val result = mcp.callTool(
            name = toolName,
            arguments = toolArgs
        )
        Logger.log("Call Tool: $result")
        return ToolUse(toolName, result?.content?.joinToString("\n") {
            (it as TextContent).text ?: ""
        })
    }

    override fun close() {
        runBlocking {
            mcp.close()
        }
    }
}