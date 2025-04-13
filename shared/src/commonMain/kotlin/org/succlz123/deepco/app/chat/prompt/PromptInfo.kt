package org.succlz123.deepco.app.chat.prompt

import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.character.TavernCardV2

@Serializable
data class PromptInfo(
    val id: Long,
    val type: PromptType,
    val name: String,
    val description: String,
    val avatar: String? = null,
    val tavernCardV2: TavernCardV2? = null,
    val links: List<String> = emptyList<String>(),
    val isDefault: Boolean = false,
    val createTime: Long = System.currentTimeMillis(),
    val updateTime: Long = createTime
)

@Serializable
enum class PromptType {
    ROLE,
    TAVERN_CARD_V2,
    NORMAL
}