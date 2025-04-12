package org.succlz123.deepco.app.llm

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val text: String? = null,
    val thinking: String? = null,
    val created: String? = null,
    val id: String? = null,
    val model: String? = null,
    var errorMsg: String? = null
) {
    var elapsedTime: Long = 0
}