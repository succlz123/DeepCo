package org.succlz123.deepco.app.ui.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_SETTING
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.i18n.LocaleLanguage
import org.succlz123.deepco.app.i18n.changeLanguage
import org.succlz123.deepco.app.mcp.biz.ToolConfig
import org.succlz123.deepco.app.theme.AppDarkThemeConfig
import org.succlz123.deepco.app.theme.AppThemeConfig
import org.succlz123.deepco.app.theme.changeAppDarkTheme
import org.succlz123.deepco.app.theme.changeAppTheme

@Serializable
data class SettingLocal(
    val llmToolMode: String = ToolConfig.Manual.name,
    val languageMode: String = LocaleLanguage.ZH.name,
    val appThemeConfig: String = AppThemeConfig.Blue.name,
    val appDarkThemeConfig: String = AppDarkThemeConfig.Sys.name
) {
}

class MainSettingViewModel : BaseBizViewModel() {

    companion object {
        val settingLocalStorage = LocalStorage(JSON_SETTING)

        fun getSettingLocal(): SettingLocal {
            return settingLocalStorage.get<SettingLocal>() ?: SettingLocal()
        }

        fun language(name: String = getSettingLocal().languageMode): LocaleLanguage {
            return when (name) {
                LocaleLanguage.ZH.name -> LocaleLanguage.ZH
                LocaleLanguage.EN.name -> LocaleLanguage.EN
                else -> LocaleLanguage.ZH
            }
        }

        fun appTheme(name: String = getSettingLocal().appThemeConfig): AppThemeConfig {
            return when (name) {
                AppThemeConfig.Blue.name -> AppThemeConfig.Blue
                AppThemeConfig.Green.name -> AppThemeConfig.Green
                AppThemeConfig.Red.name -> AppThemeConfig.Red
                AppThemeConfig.Yellow.name -> AppThemeConfig.Yellow
                else -> AppThemeConfig.Blue
            }
        }

        fun appDarkTheme(name: String = getSettingLocal().appDarkThemeConfig): AppDarkThemeConfig {
            return when (name) {
                AppDarkThemeConfig.Sys.name -> AppDarkThemeConfig.Sys
                AppDarkThemeConfig.Dark.name -> AppDarkThemeConfig.Dark
                AppDarkThemeConfig.Light.name -> AppDarkThemeConfig.Light
                else -> AppDarkThemeConfig.Sys
            }
        }
    }

    val settingLocal = MutableStateFlow(getSettingLocal())

    fun change(
        llmToolMode: String? = null,
        languageMode: String? = null,
        appTheme: String? = null,
        appThemeDark: String? = null
    ) {
        if (llmToolMode.isNullOrEmpty() && languageMode.isNullOrEmpty() && appTheme.isNullOrEmpty() && appThemeDark.isNullOrEmpty()) {
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
                changeLanguage(languageMode)
                languageMode
            },
            appThemeConfig = if (appTheme.isNullOrEmpty()) {
                setting.appThemeConfig
            } else {
                changeAppTheme(appTheme)
                appTheme
            },
            appDarkThemeConfig = if (appThemeDark.isNullOrEmpty()) {
                setting.appDarkThemeConfig
            } else {
                changeAppDarkTheme(appThemeDark)
                appThemeDark
            }
        )
        settingLocal.value = s
        settingLocalStorage.put(s)
    }
}
