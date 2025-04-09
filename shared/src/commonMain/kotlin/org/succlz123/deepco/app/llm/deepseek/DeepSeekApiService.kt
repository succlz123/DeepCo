package org.succlz123.deepco.app.llm.deepseek

import io.ktor.client.call.body
import io.ktor.client.plugins.sse.sse
import io.ktor.client.plugins.timeout
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.api.AppApiService.httpClient
import org.succlz123.deepco.app.llm.ChatMessage
import org.succlz123.deepco.app.llm.mcp.Tool
import org.succlz123.deepco.app.llm.mcp.ToolFunction
import org.succlz123.deepco.app.llm.mcp.ToolParameters
import org.succlz123.deepco.app.llm.mcp.ToolRequest
import org.succlz123.deepco.app.llm.mcp.ToolUse
import org.succlz123.lib.logger.Logger

object DeepSeekApiService {

    private const val BASE_URL = "https://api.deepseek.com"

    suspend fun chat(
        prompt: String, previousContent: List<ChatMessage>,
        apiKey: String, model: String, stream: Boolean, tools: List<List<Tool>>?, toolUses: List<ToolUse>?,
        streamCb: suspend (DeepSeekResponse, Boolean) -> Unit
    ) {
        val requestBody = DeepSeekRequest(
            model = model,
            stream = stream,
            stream_options = if (stream) {
                StreamOption(true)
            } else {
                null
            },
            frequency_penalty = 0,
            max_tokens = 8192,
            response_format = ResponseFormat("text"),
            messages = buildList {
                add(RequestMessage(prompt, "system"))
                previousContent.forEach { previous ->
                    if (previous.isFromMe) {
                        add(RequestMessage(previous.content.value, "user"))
                    } else if (!previous.isLoading()) {
                        add(RequestMessage(previous.content.value, "assistant"))
                    }
                }
                if (!toolUses.isNullOrEmpty()) {
                    for (use in toolUses) {
                        add(
                            RequestMessage(
                                """"type": "tool_result",
"tool_name": ${use.toolName},
"result": ${use.toolResult}""".trimIndent(), "user"
                            )
                        )
                    }
                }
            },
            tools = if (tools.isNullOrEmpty()) {
                null
            } else {
                tools.flatten().map {
                    ToolRequest(
                        function = ToolFunction(
                            name = it.name, description = it.description,
                            parameters = ToolParameters(
                                type = it.input_schema?.type,
                                properties = it.input_schema?.properties,
                                required = it.input_schema?.required
                            )
                        )
                    )
                }
            }
        )
        Logger.log("ReplayTask input: $requestBody")
        if (stream) {
            chatStream(requestBody, apiKey, streamCb)
        } else {
            streamCb.invoke(chatComplete(requestBody, apiKey), true)
        }
    }

    suspend fun chatComplete(requestBody: DeepSeekRequest, apiKey: String): DeepSeekResponse {
        return try {
            val response = httpClient.post("${BASE_URL}/chat/completions") {
                timeout {
                    requestTimeoutMillis = 60_000 * 15
                    connectTimeoutMillis = 60_000 * 15
                    socketTimeoutMillis = 60_000 * 15
                }
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(requestBody)
            }
            val responseStr = response.body<String>()
            Logger.log("ReplayTask result: $responseStr")
            val result = appJson.decodeFromString<DeepSeekResponse>(responseStr)
            if (result.error?.message?.isNotEmpty() == true) {
                result.apply {
                    errorMsg = result.error.message
                }
            }
            result
        } catch (e: Exception) {
            Logger.log("ReplayTask error: ${e.stackTraceToString()}")
            DeepSeekResponse().apply {
                errorMsg = e.stackTraceToString()
            }
        }
    }

    suspend fun chatStream(requestBody: DeepSeekRequest, apiKey: String, cb: suspend (DeepSeekResponse, Boolean) -> Unit) {
        try {
            httpClient.sse("${BASE_URL}/chat/completions", request = {
                method = io.ktor.http.HttpMethod.Post
                timeout {
                    requestTimeoutMillis = 60_000 * 15
                    connectTimeoutMillis = 60_000 * 15
                    socketTimeoutMillis = 60_000 * 15
                }
                header("Authorization", "Bearer $apiKey")
                header("Accept", "text/event-stream")
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }) {
                var loop = true
                while (loop) {
                    incoming.collect { event ->
                        Logger.log("ReplayTask Event from server:\n${event}")
                        val data = event.data
                        if (data?.startsWith("[DONE]") == true) {
                            loop = false
                        } else if (!data.isNullOrEmpty()) {
                            val response = try {
                                appJson.decodeFromString<DeepSeekResponse>(data)
                            } catch (e: Exception) {
                                loop = false
                                null
                            }
                            if (response != null) {
                                if (response.choices?.firstOrNull()?.finish_reason == "stop") {
                                    loop = false
                                    cb.invoke(response, true)
                                } else {
                                    cb.invoke(response, false)
                                }
                            }
                        }
                    }
                }
            }
            Logger.log("ReplayTask Event from server: OVER")
        } catch (e: Exception) {
            Logger.log("ReplayTask Ex:${e}")
            cb.invoke(DeepSeekResponse().apply {
                errorMsg = e.toString()
            }, true)
        }
    }
}

//        val startTime = System.currentTimeMillis()
//        val requestBody = DeepSeekRequest(
//            model = model,
//            stream = false,
//            frequency_penalty = 0, max_tokens = 2048,
//            response_format = ResponseFormat("text"),
////            temperature = 0.3f,
//            messages = arrayListOf(
//                Message(prompt, "system"),
//            ).apply {
//                previousContent.forEach { previous ->
//                    if (previous.isFromMe) {
//                        add(Message(previous.label, "user"))
//                    } else if (!previous.isLoading()) {
//                        add(Message(previous.label, "assistant"))
//                    }
//                }
//            }
//        )
//        val requestStr = appJson.encodeToString(requestBody)
//        var connection: HttpURLConnection? = null
//        try {
//            val url = URL("${BASE_URL}/chat/completions")
//            connection = url.openConnection() as HttpURLConnection
//            connection.apply {
//                requestMethod = "POST"
//                setRequestProperty("Content-Type", "application/json")
//                setRequestProperty("Authorization", "Bearer $apiKey")
//                connectTimeout = 1500000
//                readTimeout = 1500000
//                doOutput = true
//            }
//            Logger.log("ReplayTask: ${requestStr}")
//
//            val output: OutputStream = connection.outputStream
//            output.write(requestStr.toByteArray())
//            output.flush()
//            output.close()
//
//            val responseCode = connection.responseCode
//            val inputStream = if (responseCode in 200..299) {
//                connection.inputStream
//            } else {
//                connection.errorStream
//            }
//
//            val reader = BufferedReader(InputStreamReader(inputStream))
//            val response = StringBuilder()
//            var line: String?
//            while (reader.readLine().also { line = it } != null) {
//                response.append(line)
//            }
//            reader.close()
//
//            val responseStr = response.toString()
//            Logger.log("ReplayTask: $responseStr")
//            return appJson.decodeFromString<DeepSeekResponse>(responseStr).apply {
//                elapsedTime = (System.currentTimeMillis() - startTime)
//            }
//        } catch (e: Exception) {
//            Logger.log("ReplayTask: ${e.stackTraceToString()}")
//            return null
//        } finally {
//            connection?.disconnect()
//        }
