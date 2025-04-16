package org.succlz123.deepco.app.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppSliderBar
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun ChatModeConfigDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val mainChatViewModel = globalViewModel { MainChatViewModel() }
    BaseDialogCardWithTitleColumnScroll("Chat Mode Config") {
        val defaultConfig = mainChatViewModel.defaultChatModeConfig
        val config = mainChatViewModel.chatModeConfig.collectAsState().value
        val maxOutTokens = remember { mutableStateOf(config.maxOutTokens) }
        val temperature = remember { mutableStateOf(config.temperature) }
        val topP = remember { mutableStateOf(config.topP) }
        val topK = remember { mutableStateOf(config.topK) }
        val frequencyPenalty = remember { mutableStateOf(config.frequencyPenalty) }
        val presencePenalty = remember { mutableStateOf(config.presencePenalty) }
        Row {
            Text(
                text = "MaxOutputTokens - Default(${defaultConfig.maxOutTokens})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "介于 1 到 8192 间的整数，限制一次请求中模型生成 completion 的最大 token 数。输入 token 和输出 token 的总长度受模型的上下文长度的限制。",
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
                text = "Temperature- Default(${defaultConfig.temperature})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "采样温度，介于 0 和 2 之间。更高的值，如 0.8，会使输出更随机，而更低的值，如 0.2，会使其更加集中和确定。 我们通常建议可以更改这个值或者更改 top_p，但不建议同时对两者进行修改。",
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
                text = "TopP- Default(${defaultConfig.topP})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "作为调节采样温度的替代方案，模型会考虑前 top_p 概率的 token 的结果。所以 0.1 就意味着只有包括在最高 10% 概率中的 token 会被考虑。 我们通常建议修改这个值或者更改 temperature，但不建议同时对两者进行修改。",
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
                text = "TopK- Default(${defaultConfig.topK})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "更改模型选择输出词元的方式。如果 topK 设为 1，则表示所选 token 是模型词汇表的所有 token 中概率最高的 token；如果 topK 设为 3，则表示系统将从 3 个概率最高的 token 中选择下一个 token（通过温度确定）。系统会根据 topP 进一步过滤词元，并使用温度采样选择最终的词元。",
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
                text = "FrequencyPenalty- Default(${defaultConfig.frequencyPenalty})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其在已有文本中的出现频率受到相应的惩罚，降低模型重复相同内容的可能性。正惩罚会阻止使用在回答中已使用的令牌，从而增加词汇量。负惩罚会鼓励使用在回答中已使用的令牌，从而减少词汇量。",
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
                text = "PresencePenalty- Default(${defaultConfig.presencePenalty})", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Reset", modifier = Modifier.noRippleClick {
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
            text = "介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其是否已在已有文本中出现受到相应的惩罚，从而增加模型谈论新主题的可能性。正惩罚会阻止使用已使用的令牌，惩罚强度与令牌的使用次数成正比：令牌使用次数越多，模型就越难再次使用该令牌来增加回答的词汇量。注意：负惩罚会鼓励模型根据令牌的使用次数重复使用令牌。较小的负值会减少回答的词汇量。负值越大，模型就会开始重复一个常用令牌，直到达到 maxOutputTokens 限制。",
            modifier = Modifier,
            color = ColorResource.subText,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        AppSliderBar(modifier = Modifier.fillMaxWidth(), -2.0f, 2.0f, presencePenalty.value, true) {
            presencePenalty.value = "%.1f".format(it).toFloat()
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            AppButton(
                modifier = Modifier.align(Alignment.BottomEnd), text = "Save", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
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
    }
}