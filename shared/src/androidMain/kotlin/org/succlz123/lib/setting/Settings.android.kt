package org.succlz123.lib.setting

actual fun getAppDirPath(dirName: Array<String>): String {
    return ""
}

actual fun openConfigDir(dirName: String) {
}

actual fun saveConfig2AppDir(dirName: String, fileName: String, content: String) {
}

actual fun getConfigFromAppDir(dirName: String, fileName: String): String? {
    return null
}

actual fun getAllConfigFromAppDir(dirName: String): List<String>? {
    return null
}

actual fun removeFile(filePath: String) {
}

actual fun copyFile2ConfigDir(filePath: String, dirName: String, destName: String): String {
    return ""
}