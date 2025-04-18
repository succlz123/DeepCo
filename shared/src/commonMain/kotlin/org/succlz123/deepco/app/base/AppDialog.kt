package org.succlz123.deepco.app.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.theme.LocalAppDialogPadding
import org.succlz123.deepco.app.theme.LocalContentPadding
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.logger.Logger
import org.succlz123.lib.modifier.shadow
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenWindowSizeOwner
import org.succlz123.lib.screen.ScreenNavigator
import org.succlz123.lib.screen.window.rememberIsWindowExpanded

data class AppDialogConfig(val show: Boolean = false, val dismissByClick: Boolean = true, val onNegativeClick: (ScreenNavigator) -> Unit = {}, val onPositiveClick: (ScreenNavigator) -> Unit = {}) {

    companion object {
        val DEFAULT = AppDialogConfig()
    }
}

@Composable
fun AppDialog(
    title: String, config: State<AppDialogConfig>, content: @Composable () -> Unit
) {
    if (config.value.show) {
        val screenNavigator = LocalScreenNavigator.current
        val isExpandedScreen = rememberIsWindowExpanded()
        val screenWidth = LocalScreenWindowSizeOwner.current.getWindowHolder().size.collectAsState().value.width
        val density = LocalDensity.current.density
        val padding = remember(isExpandedScreen, screenWidth) {
            Logger.log("AppDialog padding: $isExpandedScreen, $screenWidth")
            if (isExpandedScreen) {
                (screenWidth / 4 / density).dp
            } else {
                32.dp
            }
        }
        screenNavigator.popupWindow(
            content = {
                Box(modifier = Modifier.fillMaxSize().background(ColorResource.black.copy(0.1f)).padding(LocalAppDialogPadding.current).noRippleClick {}, contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.padding(horizontal = padding)) {
                        Card(modifier = Modifier.shadow(), elevation = 0.dp, backgroundColor = Color.White, content = {
                            Column(modifier = Modifier.padding(LocalContentPadding.current)) {
                                Row {
                                    Text(
                                        modifier = Modifier,
                                        text = title,
                                        style = MaterialTheme.typography.h1,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(32.dp))
                                content()
                                Spacer(modifier = Modifier.height(48.dp))
                                Row {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        modifier = Modifier.noRippleClick {
                                            config.value.onNegativeClick(screenNavigator)
                                            if (config.value.dismissByClick) {
                                                screenNavigator.cancelPopupWindow()
                                            }
                                        },
                                        text = "Cancel",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.h3,
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.width(48.dp))
                                    Text(
                                        modifier = Modifier.noRippleClick {
                                            config.value.onPositiveClick(screenNavigator)
                                            if (config.value.dismissByClick) {
                                                screenNavigator.cancelPopupWindow()
                                            }
                                        },
                                        text = "Confirm",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.h3.copy(ColorResource.theme),
                                        maxLines = 1
                                    )
                                }
                            }
                        })
                    }
                }
            }
        )
    }
}

@Composable
fun AppMessageDialog(
    title: String, message: String,
    config: State<AppDialogConfig>
) {
    AppDialog(title, config) {
        Text(
            modifier = Modifier,
            text = message,
            style = MaterialTheme.typography.body1,
        )
    }
}

