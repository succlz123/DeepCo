package org.succlz123.deepco.app.mcp.biz

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


@Serializable
data class ToolRequest(
    val type: String = "function",
    val function: ToolFunction? = null
)

@Serializable
data class ToolFunction(
    val name: String? = null,
    val description: String? = null,
    val parameters: ToolParameters? = null,
)

@Serializable
data class ToolParameters(
    val properties: JsonObject? = null,
    val required: List<String?>? = null,
    val type: String? = null
)