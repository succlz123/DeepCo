package org.succlz123.deepco.app.mcp.biz

import kotlinx.serialization.Serializable


@Serializable
data class ToolUse(
    val toolName: String? = null,
    val toolResult: String? = null,
)
