package org.succlz123.lib.init

import androidx.compose.runtime.Composable
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.tts.TTSClient.deleteTTSSavePathOnExit
import org.succlz123.lib.common.isWindows
import org.succlz123.lib.filedownloader.core.FileDownLoader
import org.succlz123.lib.imageloader.core.ImageLoader
import java.io.File
import java.util.Locale

actual fun destructionComposeMultiplatform() {
    deleteTTSSavePathOnExit()
}

@Composable
actual fun initComposeMultiplatform() {
    if (isWindows()) {
        System.setProperty("skiko.renderApi", "OPENGL")
    }
    ImageLoader.configuration(
        rootDirectory = getCacheFolder(AppBuildConfig.APP),
        maxMemoryCacheSize = 150 * 1024 * 1024,
        maxDiskCacheSize = 300 * 1024 * 1024
    )
    FileDownLoader.configuration(rootDirectory = getCacheFolder(AppBuildConfig.APP))
}

fun getCacheFolder(dir: String): File {
    val osName = System.getProperty("os.name", "generic")
    // macOS
    if (osName.lowercase(Locale.getDefault()).contains("mac")) {
        return File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Caches" + File.separator + dir)
    }

    // Linux
    if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
        return File(System.getProperty("user.home") + File.separator + ".cache" + File.separator + dir)
    }

    // Windows
    return if (osName.contains("indows")) {
        File(System.getenv("AppData") + File.separator + dir)
    } else {
        // A reasonable fallback
        return File(System.getProperty("user.home") + File.separator + ".cache" + File.separator + dir)
    }
}