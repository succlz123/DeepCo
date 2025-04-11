package org.succlz123.deepco.app.ui.mcp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonElement
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.base.JSON_MCP
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.llm.deepseek.ToolCall
import org.succlz123.deepco.app.mcp.biz.McpConfig
import org.succlz123.deepco.app.mcp.biz.Tool
import org.succlz123.deepco.app.mcp.biz.ToolUse
import org.succlz123.deepco.app.mcp.callServerTool
import org.succlz123.deepco.app.mcp.connectToServer
import org.succlz123.lib.screen.result.ScreenResult
import org.succlz123.lib.vm.ScreenPageViewModel


class MainMcpViewModel : ScreenPageViewModel() {

    companion object {

        val MCP_SERVERS = listOf<String>("https://github.com/modelcontextprotocol/servers", "mcp.so", "glama.ai", "pulsemcp.com")
    }

    val mcpLocalStorage = LocalStorage(JSON_MCP)

    val nodeJsVersion: MutableStateFlow<String?> = MutableStateFlow(null)

    val pythonVersion: MutableStateFlow<String?> = MutableStateFlow(null)

    val UVVersion: MutableStateFlow<String?> = MutableStateFlow(null)

    val mcpList = MutableStateFlow(mcpLocalStorage.get<List<McpConfig>>().orEmpty())

    val mcpServer = MutableStateFlow(emptyMap<String, ScreenResult<List<Tool>>>())

    init {
        getNodeVersion()
        getPythonVersion()
        getUVVersion()
        initServer()
    }

    fun getNodeVersion() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                nodeJsVersion.value = ProcessBuilder("node", "--version").start().inputStream.bufferedReader().use { it.readLine() }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getPythonVersion() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                pythonVersion.value = ProcessBuilder(if (System.getProperty("os.name").lowercase().contains("win")) "python" else "python3", "--version").start().inputStream.bufferedReader().use { it.readLine() }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getUVVersion() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                UVVersion.value = ProcessBuilder("uv", "-V").start().inputStream.bufferedReader().use { it.readLine() }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun add(name: String?, command: String?, args: List<String>?) {
        if (name.isNullOrEmpty() || command.isNullOrEmpty() || args.isNullOrEmpty()) {
            return
        }
        if (mcpList.value.find { it.name == name } != null) {
            return
        }
        mcpList.value = mcpList.value + McpConfig(name, command, args, false)
        mcpLocalStorage.put(mcpList.value)
    }

    fun getServer(): Map<String, List<Tool>> {
        return mcpServer.value.mapValues { it.value.invoke().orEmpty() }
    }

    fun initServer() {
        GlobalScope.launch {
            mcpList.collect {
                it.forEach { mcp ->
                    if (mcp.disabled) {
                        return@forEach
                    }
                    if (mcpServer.value[mcp.name.orEmpty()] != null) {
                        return@forEach
                    }
                    mcpServer.value = mcpServer.value.toMutableMap().apply {
                        put(mcp.name.orEmpty(), ScreenResult.Loading())
                    }
                    val result = withContext(Dispatchers.IO) {
                        connectToServer(mcp.name.orEmpty(), mcp.command, mcp.args)
                    }
                    mcpServer.value = mcpServer.value.toMutableMap().apply {
                        put(mcp.name.orEmpty(), ScreenResult.Success(result))
                    }
                }
            }
        }
    }

    suspend fun callServer(callTools: List<ToolCall>): List<ToolUse>? {
        return callTools.mapNotNull {
            val fName = it.function?.name
            var mcpServerName = ""
            for (entry in mcpServer.value) {
                if (entry.value.invoke()?.find { it.name == fName } != null) {
                    mcpServerName = entry.key
                }
            }
            var args = mapOf<String, JsonElement>()
            val arguments = it.function?.arguments
            if (!arguments.isNullOrEmpty()) {
                args = appJson.decodeFromString<Map<String, JsonElement>>(arguments)
            }
            callServerTool(mcpServerName, it.function?.name.orEmpty(), args)
        }

    }
}
