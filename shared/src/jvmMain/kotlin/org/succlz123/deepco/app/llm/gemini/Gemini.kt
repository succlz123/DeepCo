package org.succlz123.deepco.app.llm.gemini

import com.google.genai.Client
import com.google.genai.types.Content
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GoogleSearch
import com.google.genai.types.Part
import com.google.genai.types.Tool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.msg.ChatMessage
import org.succlz123.lib.logger.Logger
import kotlin.jvm.optionals.getOrNull


// https://aistudio.google.com/app/plan_information
val map = hashMapOf<String, Client>()

private fun getClient(key: String): Client {
    var client = map[key]
    if (client == null) {
        client = Client.builder().apiKey(key).build()
        map[key] = client
    }
    return client
}

fun getConfig(prompt: String): GenerateContentConfig {
    val systemInstruction = Content.fromParts(Part.fromText(prompt))
    val googleSearchTool = Tool.builder().googleSearch(GoogleSearch.builder().build()).build()
    return GenerateContentConfig.builder()
        .candidateCount(1)
        .maxOutputTokens(1024)
        .systemInstruction(systemInstruction)
//        .tools(ImmutableList.of(googleSearchTool))
        .build()
}

actual suspend fun geminiChat(key: String, model: String, prompt: String, content: List<ChatMessage>): ChatResponse {
    return withContext(Dispatchers.Default) {
        val client = getClient(key)
        val config = getConfig(prompt)
        return@withContext withContext(Dispatchers.IO) {
            try {
                val response = client.models.generateContent(model, content.mapNotNull {
                    if (it.isFromMe) {
                        Content.fromParts(Part.fromText(it.content.value))
                    } else if (!it.isLoading()) {
                        Content.builder().role("model").parts(listOf(Part.fromText(it.content.value))).build()
                    } else {
                        null
                    }
                }, config)
                val text = response.text()
                Logger.log("geminiChat " + text.orEmpty())
                ChatResponse(
                    text = text,
                    id = response.responseId().getOrNull(),
                    created = response.createTime().getOrNull(),
                    model = response.modelVersion().getOrNull()
                )
            } catch (e: Exception) {
                Logger.log("geminiChat e" + e.message)
                ChatResponse(errorMsg = e.message.orEmpty())
            }
        }
    }
}

actual suspend fun geminiChatStream(key: String, model: String, prompt: String, content: List<ChatMessage>, cb: (ChatResponse, Boolean) -> Unit) {
    withContext(Dispatchers.Default) {
        val client = getClient(key)
        val config = getConfig(prompt)
        withContext(Dispatchers.IO) {
            try {
                val responseStream = client.models.generateContentStream(model, content.mapNotNull {
                    if (it.isFromMe) {
                        Content.fromParts(Part.fromText(it.content.value))
                    } else if (!it.isLoading()) {
                        Content.builder().role("model").parts(listOf(Part.fromText(it.content.value))).build()
                    } else {
                        null
                    }
                }, config)
                for (response in responseStream) {
                    val text = response.text()
                    Logger.log("geminiChatStream " + text.orEmpty())
                    cb.invoke(
                        ChatResponse(
                            text = text,
                            id = response.responseId().getOrNull(),
                            created = response.createTime().getOrNull(),
                            model = response.modelVersion().getOrNull()
                        ), false
                    )
                }
                responseStream.close()
                cb.invoke(ChatResponse(), true)
            } catch (e: Exception) {
                Logger.log("geminiChatStream e" + e.message)
                cb.invoke(ChatResponse(errorMsg = e.message.orEmpty()), true)
            }
        }
    }
}