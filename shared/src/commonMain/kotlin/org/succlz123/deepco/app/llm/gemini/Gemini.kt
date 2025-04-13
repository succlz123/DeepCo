package org.succlz123.deepco.app.llm.gemini

import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.chat.msg.ChatMessage
import org.succlz123.deepco.app.ui.chat.ChatModeConfig


expect suspend fun geminiChat(key: String, model: String, prompt: String, modeConfig: ChatModeConfig, content: List<ChatMessage>): ChatResponse

expect suspend fun geminiChatStream(key: String, model: String, prompt: String, modeConfig: ChatModeConfig, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit)