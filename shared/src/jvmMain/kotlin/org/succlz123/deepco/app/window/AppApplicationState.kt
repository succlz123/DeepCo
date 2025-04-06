package org.succlz123.deepco.app.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition


val LocalAppApplicationState = staticCompositionLocalOf<AppApplicationState> {
    error("LocalAppApplicationState isn't provided")
}

@Composable
fun rememberApplicationState(creator: @Composable (AppWindow) -> Unit) = remember {
    AppApplicationState().apply {
        newWindow(creator)
    }
}

class AppApplicationState {
    val tray = TrayState()
    val windows = mutableStateListOf<AppWindow>()

    fun newWindow(
        creator: @Composable (AppWindow) -> Unit,
        placement: WindowPlacement = WindowPlacement.Floating,
        isMinimized: Boolean = false,
        position: WindowPosition = WindowPosition.PlatformDefault,
        size: DpSize = DpSize(1080.dp, 720.dp)
    ) {
        windows.add(
            AppWindow(
                application = this, creator = creator, exit = windows::remove,
                placement = placement, isMinimized = isMinimized, position = position, size = size
            )
        )
    }

    fun sendNotification(notification: Notification) {
        tray.sendNotification(notification)
    }
}