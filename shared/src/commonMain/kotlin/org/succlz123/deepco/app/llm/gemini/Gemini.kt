package org.succlz123.deepco.app.llm.gemini

import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.msg.ChatMessage


expect suspend fun geminiChat(key: String, model: String, prompt: String, content: List<ChatMessage>): ChatResponse

expect suspend fun geminiChatStream(key: String, model: String, prompt: String, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit)