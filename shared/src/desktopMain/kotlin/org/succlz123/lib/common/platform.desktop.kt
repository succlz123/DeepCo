package org.succlz123.lib.common

import java.util.Locale

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun isAndroid(): Boolean {
    return false
}

actual fun isDesktop(): Boolean {
    return true
}

actual fun isWindows(): Boolean {
    val osName = System.getProperty("os.name", "generic")
    return osName.contains("indows")
}

actual fun isLinux(): Boolean {
    val osName = System.getProperty("os.name", "generic")
    if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
        return true
    }
    return false
}

actual fun isMac(): Boolean {
    val osName = System.getProperty("os.name", "generic")
    if (osName.lowercase(Locale.getDefault()).contains("mac")) {
        return true
    }
    return false
}