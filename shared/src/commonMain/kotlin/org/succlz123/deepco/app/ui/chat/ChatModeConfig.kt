package org.succlz123.deepco.app.ui.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatModeConfig(
    val maxOutTokens: Int = 4096, val temperature: Float = 1f, val topP: Float = 1f, val topK: Float = 0f, val frequencyPenalty: Float = 0f, val presencePenalty: Float = 0f
)