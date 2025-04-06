package org.succlz123.deepco

import org.succlz123.deepco.app.window.LocalAppApplicationState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.ui.window.application
import org.succlz123.lib.init.initComposeMultiplatform
import org.succlz123.deepco.app.main.MainWindow
import org.succlz123.deepco.app.window.rememberApplicationState


fun main() = application {
    initComposeMultiplatform()
    val applicationState = rememberApplicationState {
        MainWindow(it)
    }
    for (window in applicationState.windows) {
        key(window) {
            CompositionLocalProvider(LocalAppApplicationState provides applicationState) {
                window.creator.invoke(window)
            }
        }
    }
}
