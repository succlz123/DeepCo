package org.succlz123.deepco.app.llm.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


@Serializable
data class Tool(
    val name: String? = null,
    val description: String? = null,
    val input_schema: InputSchema? = null,
)

@Serializable
data class InputSchema(
    val properties: JsonObject? = null,
    val required: List<String?>? = null,
    val type: String? = null
)