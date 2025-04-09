package org.succlz123.lib.setting

expect fun getAppDirPath(dirName: Array<String>): String

expect fun openConfigDir(dirName: String)

expect fun saveConfig2AppDir(dirName: String, fileName: String, content: String)

expect fun getConfigFromAppDir(dirName: String, fileName: String): String?

