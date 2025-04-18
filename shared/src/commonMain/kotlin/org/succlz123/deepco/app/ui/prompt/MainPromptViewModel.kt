package org.succlz123.deepco.app.ui.prompt

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_PROMPT
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.character.TavernCardV2
import org.succlz123.deepco.app.chat.prompt.PromptInfo
import org.succlz123.deepco.app.chat.prompt.PromptType
import org.succlz123.deepco.app.chat.role.ChatRoleDefine

class MainPromptViewModel : BaseBizViewModel() {

    companion object {
        val CHARACTER = listOf(
            "https://stchub.vip/",
            "https://pygmalion.chat/explore",
            "https://realm.risuai.net/",
            "https://janitorai.com/search/hero",
            "https://www.chub.ai/"
        )
    }

    val promptLocalStorage = LocalStorage(JSON_PROMPT)

    val prompt = MutableStateFlow<List<PromptInfo>>(emptyList())

    init {
        val local = promptLocalStorage.get<List<PromptInfo>>().orEmpty()
        if (local.find { it.isDefault } == null) {
            promptLocalStorage.put(local.toMutableList().apply {
                addAll(ChatRoleDefine.roles)
            })
            prompt.value = ChatRoleDefine.roles
        } else {
            prompt.value = local
        }
    }

    fun changePrompt(promptInfo: PromptInfo, avatar: String, name: String, description: String) {
        prompt.value = prompt.value.toMutableList().apply {
            replaceAll {
                if (it.id == promptInfo.id) {
                    promptInfo.copy(avatar = avatar, name = name, description = description, updateTime = System.currentTimeMillis())
                } else {
                    it
                }
            }
        }
        promptLocalStorage.put(prompt.value)
    }

    fun add(type: PromptType, name: String, description: String) {
        val id = System.currentTimeMillis()
        val pro = PromptInfo(id, id, id, type, name, description)
        prompt.value = prompt.value.toMutableList().apply {
            if (this.find { it.name == name } == null) {
                add(pro)
            }
        }
        promptLocalStorage.put(prompt.value)
    }

    fun addTavernCard(id: Long, avatar: String, tavernCardV2: TavernCardV2) {
        val pro = PromptInfo(
            id, id, id, PromptType.TAVERN_CARD_V2, tavernCardV2.data.name, tavernCardV2.data.description,
            avatar = avatar,
            tavernCardV2 = tavernCardV2
        )
        prompt.value = prompt.value.toMutableList().apply {
            if (this.find { it.name == tavernCardV2.data.name } == null) {
                add(pro)
            }
        }
        promptLocalStorage.put(prompt.value)
    }

    fun remove(info: PromptInfo) {
        prompt.value = prompt.value.toMutableList().apply {
            removeIf {
                it.id == info.id
            }
        }
        promptLocalStorage.put(prompt.value)
    }
}
