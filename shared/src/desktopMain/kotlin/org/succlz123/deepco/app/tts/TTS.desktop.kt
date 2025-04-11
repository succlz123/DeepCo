package org.succlz123.deepco.app.tts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual fun speak(inputText: String) {
    GlobalScope.launch {
        withContext(Dispatchers.IO) {
            TTSClient.speak(inputText)
        }
    }
}