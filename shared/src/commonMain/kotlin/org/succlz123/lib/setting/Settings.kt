package org.succlz123.lib.setting

import com.russhwolf.settings.Settings

expect fun createSettings(name: String): Settings

expect fun getAppDirPath(dirName: Array<String>): String

expect fun saveConfig2AppDir(dirName: String, fileName: String, content: String)

expect fun getConfigFromAppDir(dirName: String, fileName: String): String?

