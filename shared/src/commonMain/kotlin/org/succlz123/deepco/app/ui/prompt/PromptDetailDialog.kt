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
import org.succlz123.deepco.app.chat.prompt.PromptInfo
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.user.UserAvatarView
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.file.choseImgFile
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel
import java.io.File

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
    val id = remember { mutableStateOf(promptInfo.id) }
    val avatar = remember { mutableStateOf(promptInfo.avatar.orEmpty()) }
    val name = remember { mutableStateOf(promptInfo.name) }
    val description = remember { mutableStateOf(promptInfo.description) }
    BaseDialogCardWithTitleColumnScroll("Prompt Detail", bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(modifier = Modifier.align(Alignment.CenterEnd)) {
                if (avatar.value != promptInfo.avatar || name.value != promptInfo.name || description.value != promptInfo.description) {
                    val savedPath = if (avatar.value == promptInfo.avatar) {
                        avatar.value
                    } else {
                        org.succlz123.lib.setting.copyFile2ConfigDir(avatar.value, AppBuildConfig.APP + File.separator + "avatar", "${id.value}")
                    }
                    vm.changePrompt(promptInfo, savedPath, name.value, description.value)
                }
                screenNavigator.pop()
            }
        }
    }) {
        Box(modifier = Modifier.fillMaxWidth().noRippleClick {
            val choseFile = choseImgFile()
            if (choseFile != null) {
                avatar.value = choseFile
            }
        }) {
            UserAvatarView(Modifier.align(Alignment.Center), 82.dp, avatar.value)
        }
        Spacer(modifier = Modifier.height(12.dp))
        AsteriskText(text = "Name")
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
        AsteriskText(text = "Description")
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
    }
}