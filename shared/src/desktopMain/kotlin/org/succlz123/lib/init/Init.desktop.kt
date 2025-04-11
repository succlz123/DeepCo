package org.succlz123.lib.init

import org.succlz123.deepco.app.tts.TTSClient.deleteTTSSavePathOnExit

actual fun destructionComposeMultiplatform() {
    deleteTTSSavePathOnExit()
}