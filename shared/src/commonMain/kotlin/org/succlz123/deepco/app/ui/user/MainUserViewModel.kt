package org.succlz123.deepco.app.ui.user

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_USER
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.chat.user.ChatUser
import org.succlz123.deepco.app.chat.user.DEFAULT_CHAT_USER

class MainUserViewModel : BaseBizViewModel() {

    val userLocalStorage = LocalStorage(JSON_USER)

    val chatUsers = MutableStateFlow<List<ChatUser>>(emptyList())

    init {
        val local = userLocalStorage.get<List<ChatUser>>().orEmpty()
        if (local.find { it.isDefault } == null) {
            userLocalStorage.put(local.toMutableList().apply {
                add(DEFAULT_CHAT_USER)
            })
            chatUsers.value = listOf(DEFAULT_CHAT_USER)
        } else {
            chatUsers.value = local
        }
        if (chatUsers.value.all { !it.isSelected }) {
            chatUsers.value = chatUsers.value.toMutableList().apply {
                val f = this.firstOrNull()
                if (f != null) {
                    remove(f)
                    add(f.copy(isSelected = true))
                }
            }
        }
    }

    fun changeUser(user: ChatUser, avatar: String, name: String, description: String) {
        chatUsers.value = chatUsers.value.toMutableList().apply {
            replaceAll {
                if (it.id == user.id) {
                    user.copy(avatar = avatar, name = name, description = description, updateTime = System.currentTimeMillis())
                } else {
                    it
                }
            }
        }
        userLocalStorage.put(chatUsers.value)
    }

    fun changeDefault(user: ChatUser) {
        chatUsers.value = chatUsers.value.map {
            it.copy(isSelected = user.id == it.id)
        }
        userLocalStorage.put(chatUsers.value)
    }

    fun add(id: Long, avatar: String, name: String, description: String) {
        chatUsers.value = chatUsers.value.toMutableList().apply {
            if (this.find { it.name == name } == null) {
                add(ChatUser(id, avatar, name, description))
            }
        }
        userLocalStorage.put(chatUsers.value)
    }

    fun remove(user: ChatUser) {
        chatUsers.value = chatUsers.value.toMutableList().apply {
            removeIf {
                it.id == user.id
            }
        }
        userLocalStorage.put(chatUsers.value)
    }
}
