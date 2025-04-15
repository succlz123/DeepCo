package org.succlz123.deepco.app.tts

import io.ikfly.constant.VoiceEnum
import io.ikfly.model.SSML
import io.ikfly.service.TTSService
import org.succlz123.lib.logger.Logger
import java.io.File

object TTSClient {
//    const val voiceName: String = "cmu-slt-hsmm"

    var isSpeak: Boolean = false

    fun getSavePath(): String {
        val file = File(System.getProperty("java.io.tmpdir") + "deepco/tts")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + File.separator
    }

    fun deleteTTSSavePathOnExit() {
        Logger.log("TTSClient: deleteTTSSavePathOnExit")
        File(getSavePath()).deleteRecursively()
    }

    fun speak(inputText: String) {
        if (inputText.isEmpty()) {
            return
        }
        if (isSpeak) {
            return
        }
        isSpeak = true
        Logger.log("TTSClient: Speak $inputText")
        try {
            val ts = TTSService()
            ts.baseSavePath = getSavePath()
            Logger.log("TTSClient: path ${ts.baseSavePath}")
            ts.sendText(
                SSML.builder()
                    .synthesisText(inputText)
                    .voice(VoiceEnum.zh_CN_XiaoxiaoNeural)
                    .usePlayer(true)
                    .build()
            )
            ts.close()
        } catch (e: Exception) {
            Logger.log("TTSClient: Speak " + e.message)
        }
//        var mary: LocalMaryInterface? = null
//        try {
//            mary = LocalMaryInterface()
//        } catch (e: MaryConfigurationException) {
//            Logger.log("TTSClient: Could not initialize MaryTTS interface " + e.message)
//        }
//        mary ?: return
//        // Set voice / language
//        mary.setVoice(voiceName)
//        // synthesize
//        var audio: AudioInputStream? = null
//        try {
//            audio = mary.generateAudio(inputText)
//        } catch (e: SynthesisException) {
//            Logger.log("TTSClient: Synthesis failed " + e.message)
//        }
//        if (audio != null) {
//            playAudio(audio)
//        }
        isSpeak = false
    }

//    fun playAudio(audio: AudioInputStream) {
//        try {
//            val clip = AudioSystem.getClip()
//            clip.open(audio)
//            clip.start()
//            while (!clip.isRunning) Thread.sleep(10)
//            while (clip.isRunning) Thread.sleep(10)
//            clip.close()
//        } catch (e: Exception) {
//            Logger.log("TTSClient: playAudio Failed " + e.message)
//        }
//    }
}