package org.succlz123.deepco.app.main

import androidx.compose.runtime.Composable
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.ui.llm.LLMAddDialog
import org.succlz123.deepco.app.ui.mcp.MCPAddDialog
import org.succlz123.lib.screen.ScreenHost
import org.succlz123.lib.screen.rememberScreenNavigator
import org.succlz123.deepco.app.base.BaseWindow
import org.succlz123.deepco.app.ui.prompt.PromptAddDialog
import org.succlz123.deepco.app.ui.prompt.PromptDetailDialog
import org.succlz123.deepco.app.window.AppWindow

@Composable
fun MainWindow(appWindow: AppWindow) {
    val screenNavigator = rememberScreenNavigator()
    BaseWindow(appWindow) {
        ScreenHost(screenNavigator = screenNavigator, rootScreenName = Manifest.MainScreen) {
            groupScreen(screenName = (Manifest.MainScreen)) {
                MainScreen()
            }
            itemScreen(screenName = (Manifest.LLMAddPopupScreen)) {
                LLMAddDialog()
            }
            itemScreen(screenName = (Manifest.MCPAddPopupScreen)) {
                MCPAddDialog()
            }
            itemScreen(screenName = (Manifest.PromptAddPopupScreen)) {
                PromptAddDialog()
            }
            itemScreen(screenName = (Manifest.PromptDetailPopupScreen)) {
                PromptDetailDialog()
            }
        }
    }
}
