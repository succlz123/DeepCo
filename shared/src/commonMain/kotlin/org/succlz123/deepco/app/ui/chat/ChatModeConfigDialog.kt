package org.succlz123.deepco.app.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import org.succlz123.deepco.app.theme.ColorResource
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
        Row {
            Text(
                text = "${strings.maxOutputTokens} - ${strings.default}(${defaultConfig.maxOutTokens})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    maxOutTokens.value = defaultConfig.maxOutTokens
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${maxOutTokens.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        // deepseek https://api-docs.deepseek.com/zh-cn/api/create-chat-completion
        // gemini https://ai.google.dev/api/generate-content?hl=zh-cn
        Text(
            text = strings.maxOutputTokensDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), 16f, 8192f, maxOutTokens.value.toFloat(), true) {
            maxOutTokens.value = it.toInt()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = "${strings.temperature} - ${strings.default}(${defaultConfig.temperature})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    temperature.value = defaultConfig.temperature
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${temperature.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = strings.temperatureDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), 0.0f, 2.0f, temperature.value, true) {
            temperature.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = "${strings.topP} - ${strings.default}(${defaultConfig.topP})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    topP.value = defaultConfig.topP
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${topP.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = strings.topPDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), 0.0f, 1.0f, topP.value, true) {
            topP.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = "${strings.topK} - ${strings.default}(${defaultConfig.topK})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    topK.value = defaultConfig.topK
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${topK.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = strings.topKDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), 0.0f, 100.0f, topK.value, true) {
            topK.value = it.toInt().toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = "${strings.frequencyPenalty} - ${strings.default}(${defaultConfig.frequencyPenalty})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    frequencyPenalty.value = defaultConfig.frequencyPenalty
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${frequencyPenalty.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = strings.frequencyPenaltyDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), -2.0f, 2.0f, frequencyPenalty.value, true) {
            frequencyPenalty.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Text(
                text = "${strings.presencePenalty} - ${strings.default}(${defaultConfig.presencePenalty})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = strings.reset, modifier = Modifier.noRippleClick {
                    presencePenalty.value = defaultConfig.presencePenalty
                }, color = ColorResource.theme, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${presencePenalty.value}", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = strings.frequencyPenaltyDescription,
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), -2.0f, 2.0f, presencePenalty.value, true) {
            presencePenalty.value = "%.1f".format(it).toFloat()
        }
    }
}