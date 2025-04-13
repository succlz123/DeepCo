package org.succlz123.lib.file

import java.awt.FileDialog

actual val HomeFolder: File
    get() = java.io.File(System.getProperty("user.home")).toProjectFile()

actual fun choseFile(suffix: List<String>): String? {
    val dialog = FileDialog(null as java.awt.Frame?, "Pic", FileDialog.LOAD)
    dialog.setFilenameFilter { dir, name ->
        suffix.find { name.endsWith(it) } != null
    }
    dialog.isVisible = true
    val file = dialog.file
    if (file != null) {
        return dialog.directory + file
    } else {
        return null
    }
}

actual fun choseImgFile(): String? {
    return choseFile(listOf(".jpg", ".avif", ".webp", ".png", ".gif", ".bmp"))
}