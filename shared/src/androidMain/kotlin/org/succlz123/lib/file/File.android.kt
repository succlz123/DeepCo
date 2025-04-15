package org.succlz123.lib.file

lateinit var _HomeFolder: java.io.File

actual val HomeFolder: File get() = _HomeFolder.toProjectFile()

actual fun choseFile(suffix: List<String>): String? {
    return ""
}

actual fun choseImgFile(): String? {
    return ""
}