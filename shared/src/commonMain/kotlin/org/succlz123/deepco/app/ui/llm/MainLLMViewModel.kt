package org.succlz123.deepco.app.ui.llm

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_LLM
import org.succlz123.deepco.app.base.LocalStorage

class MainLLMViewModel : BaseBizViewModel() {

    companion object {

        val PROVIDER_DEEPSEEK = "DeepSeek"

        val MODEL_V3 = "deepseek-chat"
        val MODEL_R1 = "deepseek-reasoner"

        val DEFAULT_LLM = listOf<LLM>(
            LLM(
                provider = PROVIDER_DEEPSEEK,
                name = "",
                modes = emptyList(),
                apiKey = "",
                baseUrl = "https://api.deepseek.com"
            )
        )
    }

    val llmLocalStorage = LocalStorage(JSON_LLM)

    val llmConfigs = MutableStateFlow(llmLocalStorage.get<LLMLocalConfig>()?.llmList.orEmpty())

    val selectedLLMName = MutableStateFlow(getDefaultLLM())

    val selectedLLMModel = MutableStateFlow(getDefaultModel())

    fun getDefaultLLM(): LLM? {
        val llm = llmLocalStorage.get<LLMLocalConfig>()
        if (llm == null) {
            return null
        }
        val selected = llm.llmList.find { it.name == llm.selectedName }
        return selected ?: llm.llmList.firstOrNull()
    }

    fun getDefaultModel(): String? {
        val llm = llmLocalStorage.get<LLMLocalConfig>()
        val modes = getDefaultLLM()?.modes
        if (modes == null) {
            return null
        }
        val selected = modes.find { it == llm?.selectedMode }
        return selected ?: modes.firstOrNull()
    }

    fun saveDefaultLLM(llm: LLM) {
        selectedLLMName.value = llm
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, llm.name, selectedLLMModel.value))
    }

    fun saveDefaultLLMModel(model: String) {
        selectedLLMModel.value = model
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, selectedLLMName.value?.name, selectedLLMModel.value))
    }

    fun add(provider: String, name: String, apiKey: String, baseUrl: String) {
        val modes = if (provider == PROVIDER_DEEPSEEK) {
            listOf(MODEL_V3, MODEL_R1)
        } else {
            emptyList()
        }
        val llm = LLM(provider, name, modes, apiKey, baseUrl)
        llmConfigs.value = llmConfigs.value.toMutableList().apply {
            add(llm)
        }
        if (selectedLLMName.value == null) {
            selectedLLMName.value = llm
            selectedLLMModel.value = llm.modes.firstOrNull()
        }
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, selectedLLMName.value?.name, selectedLLMModel.value))
    }

    fun remove(name: String) {
        llmConfigs.value = llmConfigs.value.toMutableList().apply {
            removeIf {
                it.name == name
            }
        }
        if (selectedLLMName.value?.name == name) {
            selectedLLMName.value = llmConfigs.value.firstOrNull()
            selectedLLMModel.value = llmConfigs.value.firstOrNull()?.modes?.firstOrNull()
        }
        llmLocalStorage.put(LLMLocalConfig(llmConfigs.value, selectedLLMName.value?.name, selectedLLMModel.value))
    }
}
