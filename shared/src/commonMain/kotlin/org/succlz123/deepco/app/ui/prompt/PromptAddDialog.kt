package org.succlz123.deepco.app.ui.prompt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppConfirmButton
import org.succlz123.deepco.app.base.AsteriskText
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.base.CustomExposedDropdownMenu
import org.succlz123.deepco.app.base.DropdownMenuDes
import org.succlz123.deepco.app.chat.prompt.PromptType
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel
import java.io.File

@Composable
fun PromptAddDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainPromptViewModel()
    }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf<PromptType>(PromptType.NORMAL) }
    val strings = strings()
    BaseDialogCardWithTitleColumnScroll(strings.promptConfiguration, bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(modifier = Modifier.align(Alignment.CenterEnd)) {
                if (name.value.isEmpty()) {
                    screenNavigator.toast(strings.errorNameIsEmpty)
                } else if (description.value.isEmpty()) {
                    screenNavigator.toast(strings.errorDescriptionIsEmpty)
                } else {
                    val found = vm.prompt.value.find { it.name == name.value }
                    if (found != null) {
                        screenNavigator.toast(strings.errorNameIsDuplicate)
                    } else {
                        vm.add(selectedType.value, name.value, description.value)
                        screenNavigator.pop()
                    }
                }
            }
        }
    }) {
        AsteriskText(text = strings.type)
        Spacer(modifier = Modifier.height(12.dp))
        CustomExposedDropdownMenu(listOf(PromptType.NORMAL, PromptType.ROLE).map {
            DropdownMenuDes(it.name, it)
        }, strings.selectType) { item ->
            selectedType.value = item.tag as PromptType
        }
        Spacer(modifier = Modifier.height(12.dp))
        AsteriskText(text = strings.name)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            name.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            hint = "123",
            onValueChange = {
                name.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        AsteriskText(text = strings.description)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            description.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            hint = "321",
            singleLine = false,
            scrollHeight = 250.dp,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                description.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}