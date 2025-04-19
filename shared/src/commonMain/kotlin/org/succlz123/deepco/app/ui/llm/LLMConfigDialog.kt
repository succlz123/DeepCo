package org.succlz123.deepco.app.ui.llm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.deepco.app.base.AppConfirmButton
import org.succlz123.deepco.app.base.AsteriskText
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.base.CustomExposedDropdownMenu
import org.succlz123.deepco.app.base.DropdownMenuDes
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel.Companion.DEFAULT_LLM
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel.Companion.NO_REQUIRED_API_BASE_URL
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun LLMConfigDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainLLMViewModel()
    }
    val inputLLM = LocalScreenRecord.current.arguments.value<LLM>("llm")
    val changeMode = inputLLM != null
    val id = remember {
        mutableStateOf(
            if (inputLLM == null) {
                System.currentTimeMillis()
            } else {
                inputLLM.id
            }
        )
    }
    val selectedProvider = remember { mutableStateOf<LLM?>(inputLLM) }
    val name = remember { mutableStateOf(inputLLM?.name.orEmpty()) }
    val baseUrl = remember { mutableStateOf(inputLLM?.baseUrl.orEmpty()) }
    val apiKey = remember { mutableStateOf(inputLLM?.apiKey.orEmpty()) }
    val strings = strings()
    BaseDialogCardWithTitleColumnScroll(strings.llmConfiguration, bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(Modifier.align(Alignment.BottomEnd), onClick = {
                if (selectedProvider.value?.provider.isNullOrEmpty()) {
                    screenNavigator.toast(strings.errorProviderIsEmpty)
                } else if (name.value.isEmpty()) {
                    screenNavigator.toast(strings.errorNameIsEmpty)
                } else if (!changeMode && vm.llmConfigs.value.find { it.name == name.value } != null) {
                    screenNavigator.toast(strings.errorNameIsDuplicate)
                } else if (baseUrl.value.isEmpty()) {
                    screenNavigator.toast(strings.errorBaseApiUrlIsEmpty)
                } else if (apiKey.value.isEmpty()) {
                    screenNavigator.toast(strings.errorApiKeyIsEmpty)
                } else {
                    vm.set(changeMode, id.value, selectedProvider.value?.provider.orEmpty(), name.value, selectedProvider.value?.modes.orEmpty(), apiKey.value, baseUrl.value.orEmpty())
                    screenNavigator.pop()
                }
            })
        }
    }) {
        AsteriskText(strings().provider)
        Spacer(modifier = Modifier.height(12.dp))
        CustomExposedDropdownMenu(DEFAULT_LLM.map {
            DropdownMenuDes(it.provider, it)
        }, strings().selectProvider, inputLLM?.provider) { item ->
            selectedProvider.value = item.tag as LLM
            if (inputLLM == null) {
                baseUrl.value = selectedProvider.value?.baseUrl.orEmpty()
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        AsteriskText(strings().name)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            name.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                name.value = it
            }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (baseUrl.value != NO_REQUIRED_API_BASE_URL) {
            AsteriskText(strings().baseAPIUrl)
            Spacer(modifier = Modifier.height(12.dp))
            CustomEdit(
                baseUrl.value.orEmpty(),
                textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    baseUrl.value = it
                }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        AsteriskText(strings().apiKey)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            apiKey.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                apiKey.value = it
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}