package org.succlz123.deepco.app.llm.gemini

import kotlinx.serialization.Serializable


@Serializable
data class GeminiResponse(
    val text: String? = null,
    val created: String? = null,
    val id: String? = null,
    val model: String? = null,
    var errorMsg: String? = null
) {
    var elapsedTime: Long = 0
}
