package org.succlz123.lib.setting

expect fun getAppDirPath(dirName: Array<String>): String

expect fun openConfigDir(dirName: String)

expect fun saveConfig2AppDir(dirName: String, fileName: String, content: String)

expect fun getConfigFromAppDir(dirName: String, fileName: String): String?

expect fun getAllConfigFromAppDir(dirName: String): List<String>?

expect fun removeFile(filePath: String)

expect fun copyFile2ConfigDir(filePath: String, dirName: String, destName: String): String
