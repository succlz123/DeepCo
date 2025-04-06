package org.succlz123.lib.common

import java.net.URI

actual fun openURLByBrowser(url: String) {
    try {
        java.awt.Desktop.getDesktop().browse(URI(url))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}