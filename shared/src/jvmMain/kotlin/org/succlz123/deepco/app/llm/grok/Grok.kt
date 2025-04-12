package org.succlz123.deepco.app.llm.grok

import com.anthropic.client.AnthropicClient
import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.MessageParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.msg.ChatMessage
import org.succlz123.lib.logger.Logger
import kotlin.jvm.optionals.getOrNull


val map = hashMapOf<String, AnthropicClient>()

private fun getClient(key: String): AnthropicClient {
    var client = map[key]
    if (client == null) {
        client = AnthropicOkHttpClient.builder().baseUrl("https://api.x.ai/").apiKey(key).build()
        map[key] = client
    }
    return client
}

// https://docs.x.ai/docs/models#models-and-pricing
// grok-3
// grok-3-latest
// grok-3-fast
// grok-3-fast-latest
// grok-3-mini
// grok-3-mini-latest
// grok-3-mini-fast
// grok-3-mini-fast-latest
// grok-2-vision
// grok-2-vision-latest
// grok-2-image
// grok-2-image-latest
fun getConfig(model: String): MessageCreateParams.Builder {
    return MessageCreateParams.builder().model(model).maxTokens(2048)
}

actual suspend fun grokChat(key: String, model: String, prompt: String, content: List<ChatMessage>): ChatResponse {
    return withContext(Dispatchers.Default) {
        val client = getClient(key)
        val config = getConfig(model)
        return@withContext withContext(Dispatchers.IO) {
            try {
                val messages = content.mapNotNull {
                    if (it.isFromMe) {
                        MessageParam.builder().role(MessageParam.Role.USER).content(it.content.value).build()
                    } else if (!it.isLoading()) {
                        MessageParam.builder().role(MessageParam.Role.ASSISTANT).content(it.content.value).build()
                    } else {
                        null
                    }
                }
                val params = config.messages(messages).system(prompt).build()
                val response = client.messages().create(params)
                val finalText = mutableListOf<String>()
                response.content().forEach { content ->
                    when {
                        content.isText() -> finalText.add(content.text().getOrNull()?.text() ?: "")
                    }
                }
                val resultText = finalText.joinToString("\n", prefix = "", postfix = "")
                Logger.log("grokChat " + resultText.orEmpty())
                ChatResponse(
                    text = resultText,
                    id = response.id(),
                    created = System.currentTimeMillis().toString(),
                    model = response.model().asString()
                )
            } catch (e: Exception) {
                Logger.log("grokChat e" + e.message)
                ChatResponse(errorMsg = e.message.orEmpty())
            }
        }
    }
}

actual suspend fun grokChatStream(key: String, model: String, prompt: String, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit) {
    withContext(Dispatchers.Default) {
        val client = getClient(key)
        val config = getConfig(model)
        withContext(Dispatchers.IO) {
            try {
                val messages = content.mapNotNull {
                    if (it.isFromMe) {
                        MessageParam.builder().role(MessageParam.Role.USER).content(it.content.value).build()
                    } else if (!it.isLoading()) {
                        MessageParam.builder().role(MessageParam.Role.ASSISTANT).content(it.content.value).build()
                    } else {
                        null
                    }
                }
                val params = config.messages(messages).system(prompt).build()
                var id: String? = null
                var model: String? = null
                var created: String? = null
                client.messages().createStreaming(params).use { streamResponse ->
                    streamResponse.stream().forEach { chunk ->
                        val start = chunk.contentBlockStart().getOrNull()?.contentBlock()
                        if (start != null) {
                            val text = start.text().getOrNull()?.text()
                            val toolUse = start.toolUse().getOrNull()
                            val thinking = start.thinking().getOrNull()?.thinking()

                            Logger.log("start text" + text.orEmpty())
                            Logger.log("start thinking" + thinking.orEmpty())
                        }
                        val stop = chunk.contentBlockStop().getOrNull()?.validate()?.isValid()
                        if (stop == true) {
                            Logger.log("stop " + stop)
                        }
                        val delta = chunk.contentBlockDelta().getOrNull()?.delta()
                        if (delta != null) {
                            val text = delta.text().getOrNull()?.text()
                            val thinking = delta.thinking().getOrNull()?.thinking()

                            Logger.log("delta text " + text.orEmpty())
                            Logger.log("delta thinking " + thinking.orEmpty())

                            if (!text.isNullOrEmpty()) {
                                cb.invoke(
                                    ChatResponse(
                                        text = text,
                                        id = id,
                                        created = created,
                                        model = model
                                    ), false
                                )
                            } else if (!thinking.isNullOrEmpty()) {
                                cb.invoke(
                                    ChatResponse(
                                        thinking = thinking,
                                        id = id,
                                        created = created,
                                        model = model
                                    ), false
                                )
                            }
                        }

                        val msg = chunk.start().getOrNull()?.message()
                        if (msg != null) {
                            id = msg.id()
                            model = msg.model().asString()
                            created = System.currentTimeMillis().toString()
                        }
                    }
                }
                cb.invoke(ChatResponse(text = ""), true)
            } catch (e: Exception) {
                Logger.log("grokChatStream e" + e.message)
                cb.invoke(ChatResponse(errorMsg = e.message.orEmpty()), true)
            }
        }
    }
}