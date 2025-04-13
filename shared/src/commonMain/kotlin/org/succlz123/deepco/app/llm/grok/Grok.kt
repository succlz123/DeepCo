package org.succlz123.deepco.app.llm.grok

import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.chat.msg.ChatMessage
import org.succlz123.deepco.app.ui.chat.ChatModeConfig

expect suspend fun grokChat(key: String, model: String, prompt: String, modeConfig: ChatModeConfig, content: List<ChatMessage>): ChatResponse

expect suspend fun grokChatStream(key: String, model: String, prompt: String, modeConfig: ChatModeConfig, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit)