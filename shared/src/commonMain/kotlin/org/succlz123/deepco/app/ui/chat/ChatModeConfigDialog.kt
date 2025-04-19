package org.succlz123.deepco.app.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.base.AppConfirmButton
import org.succlz123.deepco.app.base.AppSliderBar
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun ChatModeConfigDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val mainChatViewModel = globalViewModel { MainChatViewModel() }
    val defaultConfig = mainChatViewModel.defaultChatModeConfig
    val config = mainChatViewModel.chatModeConfig.collectAsState().value
    val maxOutTokens = remember { mutableStateOf(config.maxOutTokens) }
    val temperature = remember { mutableStateOf(config.temperature) }
    val topP = remember { mutableStateOf(config.topP) }
    val topK = remember { mutableStateOf(config.topK) }
    val frequencyPenalty = remember { mutableStateOf(config.frequencyPenalty) }
    val presencePenalty = remember { mutableStateOf(config.presencePenalty) }
    val strings = strings()
    BaseDialogCardWithTitleColumnScroll(strings.chatConfiguration, bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(Modifier.align(Alignment.BottomEnd), onClick = {
                mainChatViewModel.chatModeConfig.value = mainChatViewModel.chatModeConfig.value.copy(
                    maxOutTokens = maxOutTokens.value,
                    temperature = temperature.value,
                    topP = topP.value,
                    topK = topK.value,
                    frequencyPenalty = frequencyPenalty.value,
                    presencePenalty = presencePenalty.value,
                )
                mainChatViewModel.saveChatModeConfig()
                screenNavigator.pop()
            })
        }
    }) {
        // deepseek https://api-docs.deepseek.com/zh-cn/api/create-chat-completion
        // gemini https://ai.google.dev/api/generate-content?hl=zh-cn
        SliderView(strings.maxOutputTokens, strings.maxOutputTokensDescription, defaultConfig.maxOutTokens.toFloat(), maxOutTokens.value.toFloat(), 16f, 8192f) {
            maxOutTokens.value = it.toInt()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SliderView(strings.temperature, strings.temperatureDescription, defaultConfig.temperature.toFloat(), temperature.value.toFloat(), 0.0f, 2.0f) {
            temperature.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SliderView(strings.topP, strings.topPDescription, defaultConfig.topP.toFloat(), topP.value.toFloat(), 0.0f, 1.0f) {
            topP.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SliderView(strings.topK, strings.topKDescription, defaultConfig.topK.toFloat(), topK.value.toFloat(), 0.0f, 100.0f) {
            topK.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SliderView(strings.frequencyPenalty, strings.frequencyPenaltyDescription, defaultConfig.frequencyPenalty.toFloat(), frequencyPenalty.value.toFloat(), -2.0f, 2.0f) {
            frequencyPenalty.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        SliderView(strings.presencePenalty, strings.presencePenaltyDescription, defaultConfig.presencePenalty.toFloat(), presencePenalty.value.toFloat(), -2.0f, 2.0f) {
            presencePenalty.value = "%.1f".format(it).toFloat()
        }
    }
}

@Composable
fun SliderView(title: String, description: String, default: Float, value: Float, from: Float, to: Float, cb: (Float) -> Unit) {
    val strings = strings()
    Row {
        Text(
            text = "$title - ${strings.default}(${default})", modifier = Modifier, style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = strings.reset, modifier = Modifier.noRippleClick {
                cb.invoke(default)
            }, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$value", modifier = Modifier, style = MaterialTheme.typography.bodyMedium
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
        text = description,
        modifier = Modifier,
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(modifier = Modifier.height(6.dp))
    AppSliderBar(modifier = Modifier.fillMaxWidth(), from, to, value, true) {
        cb.invoke(it)
    }
}