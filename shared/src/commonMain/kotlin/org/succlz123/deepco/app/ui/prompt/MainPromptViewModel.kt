package org.succlz123.deepco.app.ui.prompt

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_PROMPT
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.llm.role.PromptInfo
import org.succlz123.deepco.app.llm.role.PromptType
import org.succlz123.deepco.app.llm.role.RoleDefine

class MainPromptViewModel : BaseBizViewModel() {

    val promptLocalStorage = LocalStorage(JSON_PROMPT)

    val prompt = MutableStateFlow<List<PromptInfo>>(emptyList())

    init {
        val local = promptLocalStorage.get<List<PromptInfo>>().orEmpty()
        if (local.find { it.isDefault } == null) {
            promptLocalStorage.put(local.toMutableList().apply {
                addAll(RoleDefine.roles)
            })
            prompt.value = RoleDefine.roles
        } else {
            prompt.value = local
        }
    }

    fun changePrompt(promptInfo: PromptInfo, name: String, description: String) {
        prompt.value = prompt.value.toMutableList().apply {
            replaceAll {
                if (it.name == promptInfo.name) {
                    promptInfo.copy(name = name, description = description, updateTime = System.currentTimeMillis())
                } else {
                    it
                }
            }
        }
        promptLocalStorage.put(prompt.value)
    }

    fun add(type: PromptType, name: String, description: String) {
        val pro = PromptInfo(type, name, description)
        prompt.value = prompt.value.toMutableList().apply {
            if (this.find { it.name == name } == null) {
                add(pro)
            }
        }
        promptLocalStorage.put(prompt.value)
    }

    fun remove(info: PromptInfo) {
        prompt.value = prompt.value.toMutableList().apply {
            removeIf {
                it.name == info.name
            }
        }
        promptLocalStorage.put(prompt.value)
    }
}
