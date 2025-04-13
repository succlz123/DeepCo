package org.succlz123.deepco.app.base

import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.json.appJson
import org.succlz123.lib.setting.getAllConfigFromAppDir
import org.succlz123.lib.setting.getConfigFromAppDir
import org.succlz123.lib.setting.removeFile
import org.succlz123.lib.setting.saveConfig2AppDir
import java.io.File

class LocalDirStorage(val dir: String) {

    inline fun <reified S> getAllFileDir(noinline default: (() -> List<S>?)? = null): List<S>? {
        val list = getAllConfigFromAppDir(AppBuildConfig.APP + File.separator + dir)
        if (list.isNullOrEmpty()) {
            return default?.invoke()
        }
        return list.mapNotNull {
            try {
                appJson.decodeFromString<S>(it)
            } catch (_: Exception) {
                null
            }
        }
    }

    inline fun <reified S> put(any: S, fileName: String) {
        saveConfig2AppDir(AppBuildConfig.APP + File.separator + dir, "$fileName.json", appJson.encodeToString(any))
    }

    fun remove(fileName: String) {
        removeFile(AppBuildConfig.APP + File.separator + dir + File.separator + "$fileName.json")
    }

    inline fun <reified S> get(fileName: String, noinline default: (() -> S?)? = null): S? {
        return try {
            val l = getConfigFromAppDir(AppBuildConfig.APP + File.separator + dir, "$fileName.json")
            if (l.isNullOrEmpty()) {
                return default?.invoke()
            }
            return appJson.decodeFromString<S>(l)
        } catch (_: Exception) {
            null
        }
    }
}