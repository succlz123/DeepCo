package org.succlz123.deepco.app.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.base.AppConfirmButton
import org.succlz123.deepco.app.base.AsteriskText
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.chat.user.ChatUser
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.file.choseImgFile
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel
import java.io.File

@Composable
fun ChatUserConfigDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainUserViewModel()
    }
    val chatUser = LocalScreenRecord.current.arguments.value<ChatUser>("item")
    val avatar = remember { mutableStateOf(chatUser?.avatar.orEmpty()) }
    val id = remember {
        mutableStateOf(
            if (chatUser == null) {
                System.currentTimeMillis()
            } else {
                chatUser.id
            }
        )
    }
    val name = remember { mutableStateOf(chatUser?.name.orEmpty()) }
    val description = remember { mutableStateOf(chatUser?.description.orEmpty()) }
    val strings = strings()
    BaseDialogCardWithTitleColumnScroll(strings.chatUserConfig, bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(Modifier.align(Alignment.BottomEnd), onClick = {
                if (name.value.isNullOrEmpty()) {
                    screenNavigator.toast(strings.errorNameIsEmpty)
                } else if (description.value.isNullOrEmpty()) {
                    screenNavigator.toast(strings.errorDescriptionIsEmpty)
                } else {
                    if (chatUser == null && vm.chatUsers.value.find { it.name == name.value } != null) {
                        screenNavigator.toast(strings.errorNameIsDuplicate)
                    } else {
                        val savedPath = if (avatar.value == chatUser?.avatar) {
                            avatar.value
                        } else {
                            org.succlz123.lib.setting.copyFile2ConfigDir(avatar.value, AppBuildConfig.APP + File.separator + "avatar", "${id.value}")
                        }
                        vm.set(chatUser != null, id.value, savedPath, name.value, description.value)
                        screenNavigator.pop()
                    }
                }
            })
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
        AsteriskText(strings().name)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            name.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            hint = "123",
            onValueChange = {
                name.value = it
            }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        AsteriskText(strings().description)
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            description.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            hint = "321",
            singleLine = false,
            scrollHeight = 160.dp,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                description.value = it
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}
