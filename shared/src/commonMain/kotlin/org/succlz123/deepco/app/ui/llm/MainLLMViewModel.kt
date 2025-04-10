package org.succlz123.deepco.app.ui.llm

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_LLM
import org.succlz123.deepco.app.base.LocalStorage

class MainLLMViewModel : BaseBizViewModel() {

    companion object {

        val PROVIDER_DEEPSEEK = "DeepSeek"
        val PROVIDER_GEMINI = "Google-Gemini"

        val MODEL_V3 = "deepseek-chat"
        val MODEL_R1 = "deepseek-reasoner"

        val MODEL_GEMINI_25_PRO_0325 = "gemini-2.5-pro-preview-03-25"
        val MODEL_GEMINI_20_FLASH = "gemini-2.0-flash"
        val MODEL_GEMINI_20_FLASH_LITE = "gemini-2.0-flash-lite"
        val MODEL_GEMINI_15_FLASH = "gemini-1.5-flash"
        val MODEL_GEMINI_15_FLASH_8B = "gemini-1.5-flash-8b"
        val MODEL_GEMINI_15_PRO = "gemini-1.5-pro"
        val MODEL_GEMINI_EMBEDDING_EXP = "gemini-embedding-exp"
        val MODEL_GEMINI_IMAGEN_30_002 = "imagen-3.0-generate-002"
        val MODEL_GEMINI_VEO_20_001 = "veo-2.0-generate-001"
        val MODEL_GEMINI_20_FLASH_LIVE_001 = "gemini-2.0-flash-live-001"

        val DEFAULT_LLM = listOf<LLM>(
            LLM(
                provider = PROVIDER_DEEPSEEK,
                name = "",
                modes = emptyList(),
                apiKey = "",
                baseUrl = "https://api.deepseek.com"
            ),
            LLM(
                provider = PROVIDER_GEMINI,
                name = "",
                modes = emptyList(),
                apiKey = "",
                baseUrl = ""
            )
        )
    }

    val llmLocalStorage = LocalStorage(JSON_LLM)

    val llmConfigs = MutableStateFlow(llmLocalStorage.get<LLMLocalConfig>()?.llmList.orEmpty())

    val selectedLLM = MutableStateFlow(getDefaultLLM())

    fun getDefaultLLM(): LLM? {
        val llm = llmLocalStorage.get<LLMLocalConfig>()
        if (llm == null) {
            return null
        }
        val selected = llm.llmList.find { it.name == llm.selectedName }
        return selected ?: llm.llmList.firstOrNull()
    }

    fun saveDefaultLLM(llm: LLM) {
        selectedLLM.value = llm
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, llm.name))
    }

    fun saveDefaultLLMModel(model: String) {
        selectedLLM.value = selectedLLM.value?.copy(defaultMode = model)
        val newConfigs = llmConfigs.value.toMutableList()
        newConfigs.replaceAll {
            val llm = selectedLLM.value
            if (it.name == llm?.name) {
                llm
            } else {
                it
            }
        }
        llmLocalStorage.put(LLMLocalConfig(newConfigs, selectedLLM.value?.name))
    }

    fun add(provider: String, name: String, apiKey: String, baseUrl: String) {
        val modes = if (provider == PROVIDER_DEEPSEEK) {
            listOf(MODEL_V3, MODEL_R1)
        } else if (provider == PROVIDER_GEMINI) {
            listOf(
                MODEL_GEMINI_25_PRO_0325, MODEL_GEMINI_20_FLASH, MODEL_GEMINI_20_FLASH_LITE, MODEL_GEMINI_15_FLASH,
                MODEL_GEMINI_15_FLASH_8B, MODEL_GEMINI_15_PRO, MODEL_GEMINI_EMBEDDING_EXP, MODEL_GEMINI_IMAGEN_30_002,
                MODEL_GEMINI_VEO_20_001, MODEL_GEMINI_20_FLASH_LIVE_001
            )
        } else {
            emptyList()
        }
        val llm = LLM(provider, name, modes, apiKey, baseUrl)
        llmConfigs.value = llmConfigs.value.toMutableList().apply {
            add(llm)
        }
        if (selectedLLM.value == null) {
            selectedLLM.value = llm
        }
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, selectedLLM.value?.name))
    }

    fun remove(name: String) {
        llmConfigs.value = llmConfigs.value.toMutableList().apply {
            removeIf {
                it.name == name
            }
        }
        if (selectedLLM.value?.name == name) {
            selectedLLM.value = llmConfigs.value.firstOrNull()
        }
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, selectedLLM.value?.name))
    }
}
