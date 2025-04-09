package org.succlz123.deepco.app.base

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_launcher
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.theme.AppTheme
import org.succlz123.lib.fps.FpsMonitor
import org.succlz123.lib.screen.ScreenContainer
import org.succlz123.deepco.app.window.AppWindow
import java.awt.Dimension

@Composable
fun BaseWindow(appWindow: AppWindow, hostLayout: @Composable (FrameWindowScope) -> Unit) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { appWindow.exit(appWindow) }

    val density = LocalDensity.current
    val minSize = DpSize(80.dp, 52.dp)
    ScreenContainer(
        enableEscBack = true,
        onCloseRequest = {
            exit()
        },
        state = appWindow.windowState,
        minimumSize = Dimension((minSize.width.value * density.density).toInt(), (minSize.height.value * density.density).toInt()),
        visible = true,
        icon = painterResource(Res.drawable.ic_launcher),
        undecorated = false,
        transparent = false,
        resizable = true,
    ) {
        window.rootPane.apply {
            rootPane.putClientProperty("apple.awt.fullWindowContent", true)
            rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
            rootPane.putClientProperty("apple.awt.windowTitleVisible", false)
        }
        AppTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                hostLayout.invoke(this@ScreenContainer)
                if (AppBuildConfig.showFPS) {
                    FpsMonitor(modifier = Modifier.padding(32.dp, 128.dp))
                }
            }
        }

        AppWindowTitleBar(appWindow.windowState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WindowScope.AppWindowTitleBar(
    windowState: WindowState
) = WindowDraggableArea(
    modifier = Modifier.combinedClickable(interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onDoubleClick = {
            windowState.placement = when (windowState.placement) {
                WindowPlacement.Floating -> WindowPlacement.Maximized
                WindowPlacement.Maximized -> WindowPlacement.Floating
                WindowPlacement.Fullscreen -> WindowPlacement.Floating
            }
        },
        onClick = {})
) {
    Box(
        Modifier.fillMaxWidth().padding(0.dp, ((64 - 25) / 2).dp), contentAlignment = Alignment.Center
    ) {
    }
}

fun FrameWindowScope.setMinimumSize(width: Int, height: Int) {
    window.minimumSize = Dimension(width, height)
}

//val density = LocalDensity.current
//LaunchedEffect(density) {
//
//}

//@Composable
//fun FrameWindowScope.setMinimumSize(size: Dp = Dp.Unspecified): Unit =
//    setMinimumSize(
//        width = size,
//        height = size,
//    )
//
//@Composable
//fun FrameWindowScope.setMinimumSize(size: DpSize = DpSize.Unspecified): Unit =
//    setMinimumSize(
//        width = size.width,
//        height = size.height,
//    )

//@Composable
//private fun FrameWindowScope.WindowMenuBar(state: AppWindowState) = MenuBar {
//    val scope = rememberCoroutineScope()
//
//    fun save() = scope.launch { state.save() }
//    fun open() = scope.launch { state.open() }
//    fun exit() = scope.launch { state.exit() }
//
//    Menu("File") {
//        Item("New window", onClick = state::newWindow)
//        Item("Open...", onClick = { open() })
//        Item("Save", onClick = { save() }, enabled = state.isChanged || state.path == null)
//        Separator()
//        Item("Exit", onClick = { exit() })
//    }
//
//    Menu("Settings") {
//        Item(
//            if (state.settings.isTrayEnabled) "Hide tray" else "Show tray",
//            onClick = state.settings::toggleTray
//        )
//        Item(
//            if (state.window.placement == WindowPlacement.Fullscreen) "Exit fullscreen" else "Enter fullscreen",
//            onClick = state::toggleFullscreen
//        )
//    }
//}