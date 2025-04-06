package org.succlz123.deepco.app.llm.deepseek

import kotlinx.serialization.Serializable

@Serializable
data class DeepSeekResponse(
    val choices: List<Choice>? = null,
    val created: Int? = null,
    val id: String? = null,
    val model: String? = null,
    val `object`: String? = null,
    val system_fingerprint: String? = null,
    val error: Error? = null,
    val usage: Usage? = null
) {

    var elapsedTime: Long = 0

    var errorMsg: String = ""
}

@Serializable
data class Choice(
    val finish_reason: String? = null,
    val index: Int? = null,
    val logprobs: String? = null,
    val message: Message? = null,
    val delta: Delta? = null
)

@Serializable
data class Usage(
    val completion_tokens: Int? = null,
    val prompt_cache_hit_tokens: Int? = null,
    val prompt_cache_miss_tokens: Int? = null,
    val prompt_tokens: Int? = null,
    val prompt_tokens_details: PromptTokensDetails? = null,
    val total_tokens: Int? = null
)

@Serializable
data class PromptTokensDetails(
    val cached_tokens: Int? = null
)

@Serializable
data class Error(
    val message: String? = null,
    val param: String? = null,
    val code: String? = null,
    val type: String? = null
)

@Serializable
data class Delta(
    val content: String? = null,
    val reasoning_content: String? = null
)


@Serializable
data class Message(
    val content: String? = null,
    val reasoning_content: String? = null,
    val content_attached: Boolean? = null,
    val content_attachment: List<String>? = null,
    val content_context: ContentContext? = null,
    val content_date: Long? = null,
    val content_error: String? = null,
    val content_status: String? = null,
    val content_usage: ContentUsage? = null,
    val role: String? = null,
    val tool_call_id: String? = null,
    val tool_calls: List<ToolCall>? = null
)

@Serializable
class ContentContext

@Serializable
data class ContentUsage(
    val completion_tokens: Int? = null,
    val prompt_tokens: Int? = null,
    val total_tokens: Int? = null
)

@Serializable
data class ToolCall(
    val index: Int? = null,
    val id: String? = null,
    val type: String? = null,
    val function: Function? = null,
    val origin_name: String? = null,
    val restore_name: String? = null,
)

@Serializable
data class Function(
    val arguments: String? = null,
    val argumentsJSON: ArgumentsJSON? = null,
    val name: String? = null
)

@Serializable
data class ArgumentsJSON(
    val districtId: String? = null
)