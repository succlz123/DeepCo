package org.succlz123.deepco.app.chat.msg

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.succlz123.lib.time.hhMMssSSS

@Serializable
data class ChatMessageData(
    val id: Long = NON_ID,
    val list: List<ChatMessage> = emptyList<ChatMessage>()
) {

    companion object {
        const val NON_ID = 0L
    }
}

object MutableStateStringSerializer : KSerializer<MutableState<String>> {
    override val descriptor = PrimitiveSerialDescriptor("MutableStateString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: MutableState<String>) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): MutableState<String> {
        return mutableStateOf(decoder.decodeString())
    }
}

@Serializable
data class ChatMessage(
    val dateTime: Long = System.currentTimeMillis(),
    var id: Long = dateTime,
    @Serializable(with = MutableStateStringSerializer::class)
    val content: MutableState<String> = mutableStateOf(""),
    @Serializable(with = MutableStateStringSerializer::class)
    val reasoningContent: MutableState<String> = mutableStateOf(""),
    val isFromMe: Boolean = false,

    var model: String = "",
    var elapsedTime: Long = 0,
    var promptTokens: Int = 0,
    var completionTokens: Int = 0,

    @Serializable(with = MutableStateStringSerializer::class)
    val toolCall: MutableState<String> = mutableStateOf(""),
    val confirmCallTool: Boolean? = null,

    val agentKey: String = "",
    val isCalled: Boolean = false,
    val isTask: Boolean = false,
    val requestType: String? = null,
    val isSend: Boolean = false,
) {

    companion object {
        const val TYPE_LOADING = -999L
    }

    fun changeContent(label: String) {
        content.value = label
    }

    fun changeReasoningContent(label: String) {
        reasoningContent.value = label
    }

    fun changeToolCall(label: String) {
        toolCall.value = label
    }

    fun isLoading(): Boolean {
        return id == TYPE_LOADING
    }

    fun createdTimeFormatted(): String {
        return dateTime.hhMMssSSS()
    }

    fun elapsedTimeFormatted(): String {
        return "%.2f".format(elapsedTime / 1000f) + "s"
    }
}