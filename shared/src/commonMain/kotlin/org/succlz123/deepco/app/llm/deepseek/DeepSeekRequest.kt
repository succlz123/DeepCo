package org.succlz123.deepco.app.llm.deepseek

import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.mcp.biz.ToolRequest

@Serializable
data class DeepSeekRequest(
    val frequency_penalty: Int? = null,
    val logprobs: Boolean? = null,
    val max_tokens: Int? = null,
    val messages: List<RequestMessage>? = null,
    val model: String? = null,
    val presence_penalty: Int? = null,
    val response_format: ResponseFormat? = null,
    val stop: Boolean? = null,
    val stream: Boolean? = null,
    val stream_options: StreamOption? = null,
    val temperature: Int? = null,
    val tool_choice: String? = null,
    val tools: List<ToolRequest>? = null,
    val top_logprobs: Boolean? = null,
    val top_p: Int? = null
)

@Serializable
data class RequestMessage(
    val content: String? = null,
    val role: String? = null
)

@Serializable
data class ResponseFormat(
    val type: String? = null
)

@Serializable
data class StreamOption(
    val include_usage: Boolean? = null
)