package org.succlz123.deepco.app.ui.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_SETTING
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.i18n.LocaleLanguage
import org.succlz123.deepco.app.i18n.language

@Serializable
data class SettingLocal(val llmToolMode: String = "manual", val languageMode: String = LocaleLanguage.ZH.name)

class MainSettingViewModel : BaseBizViewModel() {

    companion object {
        val settingLocalStorage = LocalStorage(JSON_SETTING)

        val mcpToolModeKeyList = arrayListOf("automatic", "manual")

        val languageList = arrayListOf(LocaleLanguage.ZH.name, LocaleLanguage.EN.name)

        fun getSettingLocal(): SettingLocal {
            return settingLocalStorage.get<SettingLocal>() ?: SettingLocal()
        }

        fun getLocaleLanguage(): LocaleLanguage {
            return when (getSettingLocal().languageMode) {
                LocaleLanguage.ZH.name -> LocaleLanguage.ZH
                LocaleLanguage.EN.name -> LocaleLanguage.EN
                else -> LocaleLanguage.ZH
            }
        }
    }

    val settingLocal = MutableStateFlow(getSettingLocal())

    fun change(llmToolMode: String? = null, languageMode: String? = null) {
        if (llmToolMode.isNullOrEmpty() && languageMode.isNullOrEmpty()) {
            return
        }
        val setting = settingLocal.value
        val s = setting.copy(
            llmToolMode = if (llmToolMode.isNullOrEmpty()) {
                setting.llmToolMode
            } else {
                llmToolMode
            },
            languageMode = if (languageMode.isNullOrEmpty()) {
                setting.languageMode
            } else {
                languageMode
            }
        )
        settingLocal.value = s
        settingLocalStorage.put(s)
    }
}
