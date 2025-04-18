package org.succlz123.deepco.app.chat.user

import kotlinx.serialization.Serializable

@Serializable
data class ChatUser(
    val id: Long,
    val avatar: String,
    val name: String,
    val description: String,

    val isDefault: Boolean = false,
    val isSelected: Boolean = false,

    val createTime: Long = System.currentTimeMillis(),
    val updateTime: Long = System.currentTimeMillis()
)

val DEFAULT_CHAT_USER = ChatUser(
    id = 0, avatar = "", name = "User", description = "This is the default user.", isDefault = true, isSelected = true
)