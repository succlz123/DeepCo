package org.succlz123.deepco.app.mcp

import org.succlz123.deepco.app.mcp.biz.Tool
import org.succlz123.deepco.app.mcp.biz.ToolUse

val cache = hashMapOf<String, MCPClient>()

actual suspend fun connectToServer(serverName: String, command: String?, args: List<String>?): List<Tool> {
    val mcp = cache[serverName] ?: MCPClient().also { cache[serverName] = it }
    return mcp.connectToServer(command, args)
}

actual suspend fun callServerTool(serverName: String, toolName: String, args: Map<String, Any?>): ToolUse? {
    cache[serverName]?.let { mcp ->
        return mcp.callServerTool(toolName, args)
    } ?: return null
}