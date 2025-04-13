package org.succlz123.deepco.app.base

import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.json.appJson
import org.succlz123.lib.setting.getConfigFromAppDir
import org.succlz123.lib.setting.saveConfig2AppDir

const val DIR_HISTORY = "history"
const val JSON_CHAT_MODE = "chatMode.json"
const val JSON_LLM = "llm.json"
const val JSON_MCP = "mcp.json"
const val JSON_PROMPT = "prompt.json"
const val JSON_USER = "user.json"
const val JSON_SETTING = "setting.json"

class LocalStorage(val localFileName: String) {

    companion object {

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
        saveConfig2AppDir(AppBuildConfig.APP, localFileName, appJson.encodeToString(any))
    }

    inline fun <reified S> get(noinline default: (() -> S?)? = null): S? {
        return try {
            val l = getConfigFromAppDir(AppBuildConfig.APP, localFileName)
            if (l.isNullOrEmpty()) {
                return default?.invoke()
            }
            return appJson.decodeFromString<S>(l)
        } catch (e: Exception) {
            null
        }
    }
}