package org.succlz123.deepco.app.base

import org.succlz123.deepco.app.json.appJson
import org.succlz123.lib.setting.getConfigFromAppDir
import org.succlz123.lib.setting.saveConfig2AppDir

const val JSON_HISTORY = "history.json"
const val JSON_LLM = "llm.json"
const val JSON_MCP = "mcp.json"
const val JSON_PROMPT = "prompt.json"
const val JSON_SETTING = "setting.json"

class LocalStorage(val localFileName: String) {

    companion object {
        const val APP = "deepco"

        fun stringToUniqueIntId(input: String): Int {
            var hashCode = input.hashCode()
            if (hashCode < 0) {
                hashCode = -hashCode
            }
            val offset = input.length * 1000
            hashCode += offset
            return hashCode
        }
    }

    inline fun <reified S> put(any: S) {
        saveConfig2AppDir(APP, localFileName, appJson.encodeToString(any))
    }

    inline fun <reified S> get(noinline default: (() -> S?)? = null): S? {
        return try {
            val l = getConfigFromAppDir(APP, localFileName)
            if (l.isNullOrEmpty()) {
                return default?.invoke()
            }
            return appJson.decodeFromString<S>(l)
        } catch (e: Exception) {
            null
        }
    }
}