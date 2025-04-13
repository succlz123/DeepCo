package org.succlz123.lib.click

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.succlz123.lib.common.openURLByBrowser
import org.succlz123.lib.screen.LocalScreenNavigator

fun Modifier.noRippleClick(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.noDoubleClick(onClick: () -> Unit): Modifier = composed {
    pointerInput(Unit) {
        detectTapGestures(onDoubleTap = {
            onClick.invoke()
        })
    }
}

fun Modifier.noDoubleClickAndCopyStr(str: String, showToast: Boolean = false): Modifier = composed {
    val clipboardManager = LocalClipboardManager.current
    val screenNavigator = LocalScreenNavigator.current
    pointerInput(Unit) {
        detectTapGestures(onDoubleTap = {
            clipboardManager.setText(AnnotatedString(str))
            if (showToast) {
                screenNavigator.toast("Copied: $str")
            }
        })
    }
}

fun Modifier.onClickAndCopyStr(str: String, showToast: Boolean = false): Modifier = composed {
    val clipboardManager = LocalClipboardManager.current
    val screenNavigator = LocalScreenNavigator.current
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            clipboardManager.setText(AnnotatedString(str))
            if (showToast) {
                screenNavigator.toast("Copied")
            }
        })
    }
}

fun Modifier.onClickAndSpeakStr(str: String): Modifier = composed {
    val screenNavigator = LocalScreenNavigator.current
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            screenNavigator.toast("Speaking...")
            org.succlz123.deepco.app.tts.speak(str)
        })
    }
}

fun Modifier.onClickUrl(url: String): Modifier = composed {
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            openURLByBrowser(url)
        })
    }
}

fun Modifier.soundClick(onClick: () -> Unit): Modifier = composed {
    val soundInteraction = remember {
//        val mediaPlayer = CallbackMediaPlayerComponent().mediaPlayer()
//        mediaPlayer.media()?.prepare("")
        val interaction = object : MutableInteractionSource {

            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                interactions.emit(interaction)
                if (interaction is HoverInteraction.Enter) {
//                    mediaPlayer.controls().play()
                }
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
        interaction
    }
    clickable(indication = null, interactionSource = soundInteraction) {
        onClick()
    }
}