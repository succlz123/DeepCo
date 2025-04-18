package org.succlz123.deepco.app.main

import androidx.compose.runtime.Composable
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.ui.chat.ChatModeConfigDialog
import org.succlz123.deepco.app.ui.llm.LLMConfigDialog
import org.succlz123.deepco.app.ui.mcp.MCPAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptCharacterAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptDetailDialog
import org.succlz123.deepco.app.ui.user.ChatUserConfigDialog
import org.succlz123.deepco.app.ui.prompt.PromptSelectedDialog
import org.succlz123.deepco.app.ui.user.ChatUserSelectDialog
import org.succlz123.lib.screen.ScreenHost
import org.succlz123.lib.screen.rememberScreenNavigator

@Composable
fun MainActivity() {
    val screenNavigator = rememberScreenNavigator()
    ScreenHost(screenNavigator = screenNavigator, rootScreenName = Manifest.MainScreen) {
        groupScreen(screenName = (Manifest.MainScreen)) {
            MainScreen()
        }
        itemScreen(screenName = (Manifest.LLMConfigPopupScreen)) {
            LLMConfigDialog()
        }
        itemScreen(screenName = (Manifest.ChatModeConfigPopupScreen)) {
            ChatModeConfigDialog()
        }
        itemScreen(screenName = (Manifest.MCPAddPopupScreen)) {
            MCPAddDialog()
        }
        itemScreen(screenName = (Manifest.PromptAddPopupScreen)) {
            PromptAddDialog()
        }
        itemScreen(screenName = (Manifest.PromptCharacterAddPopupScreen)) {
            PromptCharacterAddDialog()
        }
        itemScreen(screenName = (Manifest.PromptDetailPopupScreen)) {
            PromptDetailDialog()
        }
        itemScreen(screenName = (Manifest.PromptSelectedPopupScreen)) {
            PromptSelectedDialog()
        }
        itemScreen(screenName = (Manifest.ChatUserConfigPopupScreen)) {
            ChatUserConfigDialog()
        }
        itemScreen(screenName = (Manifest.ChatUserSelectPopupScreen)) {
            ChatUserSelectDialog()
        }
    }
}