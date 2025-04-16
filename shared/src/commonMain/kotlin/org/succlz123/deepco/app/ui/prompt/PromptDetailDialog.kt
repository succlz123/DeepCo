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
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.chat.prompt.PromptInfo
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun PromptDetailDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val promptInfo = LocalScreenRecord.current.arguments.value<PromptInfo>("item")
    if (promptInfo == null) {
        screenNavigator.toast("Prompt Info is Null!")
        screenNavigator.pop()
        return
    }
    val vm = globalViewModel {
        MainPromptViewModel()
    }
    BaseDialogCardWithTitleColumnScroll("Prompt Detail") {
        val name = remember { mutableStateOf(promptInfo.name) }
        val description = remember { mutableStateOf(promptInfo.description) }
        Text(
            text = "Name", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            name.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                name.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Description", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            description.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = false,
            maxLines = 10,
            onValueChange = {
                description.value = it
            }, scrollHeight = 250.dp, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            AppButton(
                modifier = Modifier.align(Alignment.BottomEnd), text = "Save", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
                    if (name.value != promptInfo.name || description.value != promptInfo.description) {
                        vm.changePrompt(promptInfo, name.value, description.value)
                    }
                    screenNavigator.pop()
                })
        }
    }
}