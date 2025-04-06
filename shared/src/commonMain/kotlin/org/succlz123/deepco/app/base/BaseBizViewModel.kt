package org.succlz123.deepco.app.base

import com.russhwolf.settings.set
import org.succlz123.lib.setting.createSettings
import org.succlz123.lib.setting.saveConfig2AppDir
import org.succlz123.lib.setting.getConfigFromAppDir
import org.succlz123.lib.vm.BaseViewModel
import java.net.InetAddress

open class BaseBizViewModel : BaseViewModel() {

    companion object {
        const val APP = "deposition"
        val SETTING = createSettings(APP)

        const val HISTORY_JSON = "history.json"

        const val LLM_JSON = "llm.json"

        const val MCP_JSON = "mcp.json"

        const val KEY_ADDRESS = "KEY_ADDRESS"
        const val KEY_HOSTNAME = "KEY_HOSTNAME"

        fun get(key: String, default: String = ""): String {
            return getConfigFromAppDir(APP, key) ?: default
        }

        fun put(key: String, value: String) {
            saveConfig2AppDir(APP, key, value)
        }

        fun settingAddress(): String {
            return SETTING.getString(KEY_ADDRESS, "127.0.0.1:3030")
        }

        fun saveAddress(address: String) {
            SETTING[KEY_ADDRESS] = address
        }

        fun settingHostname(): String {
            return SETTING.getString(
                KEY_HOSTNAME, try {
                    val name = InetAddress.getLocalHost().hostName
                    if (name.isEmpty()) {
                        throw NullPointerException()
                    }
                    name
                } catch (e: Exception) {
                    "名字:" + System.currentTimeMillis()
                }
            )
        }

        fun saveHostname(name: String) {
            SETTING[KEY_HOSTNAME] = name
        }

        fun settingHostId(): String {
            return stringToUniqueIntId(settingHostname()).toString()
        }

        fun stringToUniqueIntId(input: String): Int {
            // 使用字符串的哈希码作为基础
            var hashCode = input.hashCode()

            // 确保哈希码为正数
            if (hashCode < 0) {
                hashCode = -hashCode
            }

            // 添加一个偏移量以避免与其他字符串的哈希码冲突
            val offset = input.length * 1000
            hashCode += offset

            // 返回唯一的整数 ID
            return hashCode
        }
    }

    val setting = SETTING

}