package org.succlz123.deepco.app.ui.llm

import kotlinx.serialization.Serializable
import org.succlz123.lib.time.hhMMssSSS

@Serializable
data class LLM(
    val provider: String,
    val name: String,
    val modes: List<String>,
    val apiKey: String,
    val baseUrl: String,
    private val defaultMode: String? = null,
    val createData: Long = System.currentTimeMillis(),
) {

    fun createDataFmt(): String {
        return createData.hhMMssSSS()
    }

    fun getMaskedKey(): String {
        return apiKey.replaceRange(3, apiKey.length - 3, "****")
    }

    fun getSelectedModeMode(): String {
        return if (defaultMode.isNullOrEmpty()) {
            return modes.firstOrNull().orEmpty()
        } else {
            defaultMode
        }
    }
}

@Serializable
data class LLMLocalConfig(
    val llmList: List<LLM>,
    val selectedName: String?
)

