package org.succlz123.deepco.app.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

class AppWindow(
    val application: AppApplicationState,
    val creator: @Composable (AppWindow) -> Unit,
    val exit: (AppWindow) -> Unit,
    val placement: WindowPlacement,
    val isMinimized: Boolean,
    val position: WindowPosition,
    val size: DpSize
) {
    val windowState: WindowState = WindowState(placement = placement, isMinimized = isMinimized, position = position, size = size)

    var isInit by mutableStateOf(false)
        private set

    fun toggleFullscreen() {
        windowState.placement = if (windowState.placement == WindowPlacement.Fullscreen) {
            WindowPlacement.Floating
        } else {
            WindowPlacement.Fullscreen
        }
    }

    private fun initNew() {
        isInit = true
    }
}