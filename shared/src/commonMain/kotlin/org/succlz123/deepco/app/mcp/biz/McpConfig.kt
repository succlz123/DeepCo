package org.succlz123.deepco.app.mcp.biz

import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.base.TabIndexGet

@Serializable
data class McpConfig(
    val name: String?,
    val command: String?,
    val args: List<String>?,
    val disabled: Boolean
) : TabIndexGet {

    override fun getFieldByIndex(index: Int): String? {
        return if (index == 0) {
            name
        } else if (index == 1) {
            command
        } else if (index == 2) {
            args.orEmpty().joinToString(separator = " ")
        } else if (index == 3) {
            disabled.toString()
        } else {
            null
        }
    }

}
