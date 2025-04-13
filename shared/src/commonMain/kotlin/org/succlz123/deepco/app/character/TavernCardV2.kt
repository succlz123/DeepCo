package org.succlz123.deepco.app.character

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// https://github.com/malfoyslastname/character-card-spec-v2
// https://github.com/bradennapier/character-cards-v2?tab=readme-ov-file
@Serializable
data class TavernCardV2(
    val spec: String = "chara_card_v2",
    val spec_version: String = "2.0",
    val data: TavernCardData
)

@Serializable
data class TavernCardData(
    val name: String,
    val description: String,
    val personality: String? = null,
    val scenario: String? = null,
    val first_mes: String? = null,
    val mes_example: String? = null,

    val creator_notes: String? = null,
    val system_prompt: String? = null,
    val post_history_instructions: String? = null,
    val alternate_greetings: List<String>? = null,
    val character_book: CharacterBook? = null,

    val tags: List<String>? = null,
    val creator: String? = null,
    val character_version: String? = null,
    val extensions: Map<String, JsonElement>? = null
)

@Serializable
data class CharacterBook(
    val name: String? = null,
    val description: String? = null,
    val scan_depth: Int? = null,
    val token_budget: Int? = null,
    val recursive_scanning: Boolean? = null,
    val extensions: Map<String, JsonElement> = emptyMap(),
    val entries: List<CharacterBookEntry>? = null,
) {
    companion object {
        const val POSITION_BEFORE_CHAR = "before_char"
        const val POSITION_AFTER_CHAR = "after_char"
    }
}

@Serializable
data class CharacterBookEntry(
    val keys: List<String>? = null,
    val content: String? = null,
    val extensions: Map<String, JsonElement> = emptyMap(),
    val enabled: Boolean? = null,
    val insertion_order: Int? = null,
    val case_sensitive: Boolean? = null,

    val name: String? = null,
    val priority: Int? = null,

    val id: Int? = null,
    val comment: String? = null,
    val selective: Boolean? = null,
    val secondary_keys: List<String>? = null,
    val constant: Boolean? = null,
    val position: String? = null  // "before_char" or "after_char"
)