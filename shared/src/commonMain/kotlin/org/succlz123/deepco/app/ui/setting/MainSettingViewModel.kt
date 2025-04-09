package org.succlz123.deepco.app.ui.setting

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.JSON_SETTING
import org.succlz123.deepco.app.base.LocalStorage

@Serializable
data class SettingLocal(val llmToolMode: String? = null)

class MainSettingViewModel : BaseBizViewModel() {

    companion object {

        val llmToolModeList = arrayListOf("Automatic", "Manual")
    }

    val settingLocalStorage = LocalStorage(JSON_SETTING)

    val settingLocal = MutableStateFlow(settingLocalStorage.get<SettingLocal>())
}
