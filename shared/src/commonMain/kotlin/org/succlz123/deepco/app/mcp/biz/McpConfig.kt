package org.succlz123.deepco.app.mcp.biz

import kotlinx.serialization.Serializable

@Serializable
data class McpConfig(
    val name: String?,
    val command: String?,
    val args: List<String>?,
    val disabled: Boolean
)
