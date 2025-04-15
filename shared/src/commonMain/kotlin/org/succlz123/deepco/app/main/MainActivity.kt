package org.succlz123.deepco.app.main

import androidx.compose.runtime.Composable
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.ui.chat.ChatModeConfigDialog
import org.succlz123.deepco.app.ui.llm.LLMAddDialog
import org.succlz123.deepco.app.ui.mcp.MCPAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptCharacterAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptDetailDialog
import org.succlz123.deepco.app.ui.user.ChatUserAddDialog
import org.succlz123.deepco.app.ui.user.ChatUserDetailDialog
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
        itemScreen(screenName = (Manifest.LLMAddPopupScreen)) {
            LLMAddDialog()
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
        itemScreen(screenName = (Manifest.ChatUserAddPopupScreen)) {
            ChatUserAddDialog()
        }
        itemScreen(screenName = (Manifest.ChatUserSelectPopupScreen)) {
            ChatUserSelectDialog()
        }
        itemScreen(screenName = (Manifest.ChatUserDetailPopupScreen)) {
            ChatUserDetailDialog()
        }
    }
}