package org.succlz123.deepco.app.ui.setting

import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.base.BaseBizViewModel

class MainSettingViewModel : BaseBizViewModel() {

    companion object {

        val KEY_LLM_TOOL_MODE = "KEY_LLM_TOOL_MODE"
        val llmToolModeList = arrayListOf("Automatic", "Manual")

        val KEY_FPS = "KEY_FPS"
        val fpsList = arrayListOf("enable", "disable")

        fun llmToolSetting(): String {
            return SETTING.getString(KEY_LLM_TOOL_MODE, "forward")
        }

        fun fpsFromSetting(): String {
            return SETTING.getString(KEY_FPS, "disable")
        }
    }

    val llmToolMode = MutableStateFlow(llmToolSetting())

    val fpsMode = MutableStateFlow(fpsFromSetting())
}
