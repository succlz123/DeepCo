package org.succlz123.deepco.app

object AppBuildConfig {
    var isDebug = true
    var showFPS = false

    val APP = "deepco"
    val gitHubUrl = "https://github.com/succlz123/DeepCo"

    var versionName = "1.0.5"
    var versionCode = 2
}

object Manifest {
    const val MainScreen = "MainScreen"

    const val ChatModeConfigPopupScreen = "ChatConfigPopupScreen"
    const val LLMConfigPopupScreen = "LLMAddPopupScreen"
    const val MCPAddPopupScreen = "MCPAddPopupScreen"
    const val PromptAddPopupScreen = "PromptAddPopupScreen"
    const val PromptCharacterAddPopupScreen = "PromptCharacterAddPopupScreen"
    const val PromptDetailPopupScreen = "PromptDetailPopupScreen"
    const val PromptSelectedPopupScreen = "ChatUserDetailPopupScreen"
    const val ChatUserConfigPopupScreen = "ChatUserAddPopupScreen"
    const val ChatUserSelectPopupScreen = "ChatUserSelectPopupScreen"
}
