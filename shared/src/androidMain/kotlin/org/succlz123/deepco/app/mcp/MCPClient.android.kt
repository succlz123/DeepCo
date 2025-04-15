package org.succlz123.deepco.app.mcp

import org.succlz123.deepco.app.mcp.biz.Tool
import org.succlz123.deepco.app.mcp.biz.ToolUse

actual suspend fun connectToServer(serverName: String, command: String?, args: List<String>?): List<Tool> {
    return emptyList()
}

actual suspend fun callServerTool(serverName: String, toolName: String, args: Map<String, Any?>): ToolUse? {
    return null
}