package org.succlz123.deepco.app.llm.grok

import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.msg.ChatMessage

expect suspend fun grokChat(key: String, model: String, prompt: String, content: List<ChatMessage>): ChatResponse

expect suspend fun grokChatStream(key: String, model: String, prompt: String, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit)