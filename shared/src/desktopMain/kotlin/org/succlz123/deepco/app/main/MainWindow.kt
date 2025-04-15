package org.succlz123.deepco.app.main

import androidx.compose.runtime.Composable
import org.succlz123.deepco.app.base.BaseWindow
import org.succlz123.deepco.app.window.AppWindow
import org.succlz123.lib.init.destructionComposeMultiplatform

@Composable
fun MainWindow(appWindow: AppWindow) {
    BaseWindow(appWindow, onCloseRequest = {
        destructionComposeMultiplatform()
    }) {
        MainActivity()
    }
}
