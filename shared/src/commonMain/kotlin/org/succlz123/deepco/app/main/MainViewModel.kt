package org.succlz123.deepco.app.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.ui.chat.MainChatTab
import org.succlz123.deepco.app.ui.llm.MainLLMTab
import org.succlz123.deepco.app.ui.mcp.MainMcpTab
import org.succlz123.deepco.app.ui.prompt.MainPromptTab
import org.succlz123.deepco.app.ui.setting.MainSettingTab
import org.succlz123.deepco.app.ui.user.MainUserTab
import org.succlz123.lib.common.isDesktop
import org.succlz123.lib.vm.BaseViewModel

data class MainSelectItem(val name: @Composable () -> String, val icon: ImageVector, val content: @Composable () -> Unit)

class MainViewModel : BaseViewModel() {

    companion object {

        val MAIN_TITLE = buildList {
            add(MainSelectItem(@Composable {
                strings().tabChat
            }, Icons.Sharp.Home, @Composable {
                MainChatTab()
            }))
            add(MainSelectItem(@Composable { strings().tabLLM }, Icons.Sharp.Star, @Composable {
                MainLLMTab()
            }))
            add(MainSelectItem(@Composable { strings().tabPrompt }, Icons.Sharp.Add, @Composable {
                MainPromptTab()
            }))
            add(MainSelectItem(@Composable { strings().tabUser }, Icons.Sharp.Person, @Composable {
                MainUserTab()
            }))
            if (isDesktop()) {
                add(MainSelectItem(@Composable { strings().tabMCP }, Icons.Sharp.Build, @Composable {
                    MainMcpTab()
                }))
            }
            add(MainSelectItem(@Composable { strings().tabSetting }, Icons.Sharp.Settings, @Composable {
                MainSettingTab()
            }))
        }
    }

    val selectItem = MutableStateFlow<MainSelectItem>(MAIN_TITLE.first())
}
