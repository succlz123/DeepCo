package org.succlz123.deepco.app.ui.llm

import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.base.TabIndexGet
import org.succlz123.lib.time.hhMMssSSS

@Serializable
data class LLM(
    val provider: String,
    val name: String,
    val modes: List<String>,
    val apiKey: String,
    val baseUrl: String,
    val id: Long,
    val createTime: Long,
    val updateTime: Long,
    private val defaultMode: String? = null,
) : TabIndexGet {

    fun createDataFmt(): String {
        return createTime.hhMMssSSS()
    }

    fun getMaskedKey(): String {
        return if (apiKey.isEmpty()) {
            ""
        } else {
            apiKey.replaceRange(3, apiKey.length - 3, "****")
        }
    }

    fun getSelectedModeMode(): String {
        return if (defaultMode.isNullOrEmpty()) {
            return modes.firstOrNull().orEmpty()
        } else {
            defaultMode
        }
    }

    override fun getFieldByIndex(index: Int): String? {
        return if (index == 0) {
            provider
        } else if (index == 1) {
            name
        } else if (index == 2) {
            modes.joinToString(separator = "\n")
        } else if (index == 3) {
            baseUrl
        } else if (index == 4) {
            getMaskedKey()
        } else if (index == 5) {
            createDataFmt()
        } else {
            null
        }
    }
}

@Serializable
data class LLMLocalConfig(
    val llmList: List<LLM>,
    val selectedName: String?
)

