package org.succlz123.lib.common

actual fun getPlatformName(): String {
    return "Android"
}

actual fun isAndroid(): Boolean {
    return true
}

actual fun isDesktop(): Boolean {
    return false
}

actual fun isWindows(): Boolean {
    return false
}

actual fun isLinux(): Boolean {
    return false
}

actual fun isMac(): Boolean {
    return false
}