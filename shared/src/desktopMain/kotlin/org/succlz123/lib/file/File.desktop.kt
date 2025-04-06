package org.succlz123.lib.file

actual val HomeFolder: File
    get() = java.io.File(System.getProperty("user.home")).toProjectFile()