package org.succlz123.deepco.app.ui.llm

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.base.BaseBizViewModel

class MainLLMViewModel : BaseBizViewModel() {

    companion object {

        val PROVIDER_DEEPSEEK = "DeepSeek"

        val MODEL_V3 = "deepseek-chat"
        val MODEL_R1 = "deepseek-reasoner"

        fun saveLLM(llmList: List<LLM>, selectedLLM: String?, selectedLLMModel: String?) {
            put(LLM_JSON, appJson.encodeToString(LLMLocalConfig(llmList, selectedLLM, selectedLLMModel)))
        }

        fun getLLM(): LLMLocalConfig? {
            return try {
                appJson.decodeFromString<LLMLocalConfig>(get(LLM_JSON))
            } catch (e: Exception) {
                null
            }
        }

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

    val llmConfigs = MutableStateFlow(getLLM()?.llmList.orEmpty())

    val selectedLLMName = MutableStateFlow(getDefaultLLM())

    val selectedLLMModel = MutableStateFlow(getDefaultModel())

    fun getDefaultLLM(): LLM? {
        val llm = getLLM()
        if (llm == null) {
            return null
        }
        val selected = llm.llmList.find { it.name == llm.selectedName }
        return selected ?: llm.llmList.firstOrNull()
    }

    fun getDefaultModel(): String? {
        val llm = getLLM()
        val modes = getDefaultLLM()?.modes
        if (modes == null) {
            return null
        }
        val selected = modes.find { it == llm?.selectedMode }
        return selected ?: modes.firstOrNull()
    }

    fun saveDefaultLLM(llm: LLM) {
        selectedLLMName.value = llm
        saveLLM(llmConfigs.value, llm.name, selectedLLMModel.value)
    }

    fun saveDefaultLLMModel(model: String) {
        selectedLLMModel.value = model
        saveLLM(llmConfigs.value, selectedLLMName.value?.name, model)
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
        saveLLM(llmConfigs.value, selectedLLMName.value?.name, selectedLLMModel.value)
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
        saveLLM(llmConfigs.value, selectedLLMName.value?.name, selectedLLMModel.value)
    }
}
