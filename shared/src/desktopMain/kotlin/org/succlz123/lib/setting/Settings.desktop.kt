package org.succlz123.lib.setting

import java.io.File
import java.nio.file.Paths
import java.util.Locale

actual fun getAppDirPath(dirName: Array<String>): String {
    val os = System.getProperty("os.name").lowercase(Locale.ROOT)
    return when {
        os.contains("win") -> Paths.get(
            System.getProperty("user.home"),
            "AppData",
            "Local",
            *dirName
        ).toString()

        else -> Paths.get(
            System.getProperty("user.home"),
            ".config",
            *dirName
        ).toString()
    }
}

actual fun saveConfig2AppDir(dirName: String, fileName: String, content: String) {
    val file = File(getAppDirPath(arrayOf(dirName, fileName)))
    if (file.parentFile?.exists() != true) {
        file.parentFile?.mkdirs()
    }
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText(content)
}

actual fun getConfigFromAppDir(dirName: String, fileName: String): String? {
    val file = File(getAppDirPath(arrayOf(dirName, fileName)))
    if (file.exists()) {
        return file.readText()
    }
    return null
}

actual fun openConfigDir(dirName: String) {
    try {
        val desktop = java.awt.Desktop.getDesktop()
        val appDir = File(getAppDirPath(arrayOf(dirName)))
        if (appDir.exists()) {
            desktop.open(appDir)
        } else {
            appDir.mkdirs()
            desktop.open(appDir)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

actual fun getAllConfigFromAppDir(dirName: String): List<String>? {
    val file = File(getAppDirPath(arrayOf(dirName)))
    return if (file.isDirectory) {
        file.listFiles().map {
            it.readText()
        }
    } else {
        emptyList()
    }
}

actual fun removeFile(filePath: String) {
    val file = File(getAppDirPath(arrayOf(filePath)))
    if (file.exists()) {
        file.delete()
    }
}