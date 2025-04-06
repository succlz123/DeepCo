package org.succlz123.deepco.app.llm.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


@Serializable
data class ToolUse(
    val toolName: String? = null,
    val toolResult: String? = null,
)
