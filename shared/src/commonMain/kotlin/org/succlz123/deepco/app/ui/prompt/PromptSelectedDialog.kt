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
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.chat.user.ChatUser
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.user.MainUserViewModel
import org.succlz123.deepco.app.ui.user.UserAvatarView
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.file.choseImgFile
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel
import org.succlz123.lib.setting.copyFile2ConfigDir
import java.io.File

@Composable
fun PromptSelectedDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val chatUser = LocalScreenRecord.current.arguments.value<ChatUser>("item")
    if (chatUser == null) {
        screenNavigator.toast("ChatUser is Null!")
        screenNavigator.pop()
        return
    }
    val vm = globalViewModel {
        MainUserViewModel()
    }
    val avatar = remember { mutableStateOf(chatUser.avatar) }
    val id = remember { mutableStateOf(chatUser.id) }
    val name = remember { mutableStateOf(chatUser.name) }
    val description = remember { mutableStateOf(chatUser.description) }
    val strings = strings()
    BaseDialogCardWithTitleColumnScroll("Chat User Detail") {

        Box(modifier = Modifier.fillMaxWidth().noRippleClick {
            val choseFile = choseImgFile()
            if (choseFile != null) {
                avatar.value = choseFile
            }
        }) {
            UserAvatarView(Modifier.align(Alignment.Center), 82.dp, avatar.value)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Name", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
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
        Text(
            text = "Description", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
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
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            AppButton(
                modifier = Modifier.align(Alignment.BottomEnd), text = "Save", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
                    if (name.value.isNullOrEmpty()) {
                        screenNavigator.toast("Name is empty!")
                    } else if (description.value.isNullOrEmpty()) {
                        screenNavigator.toast("Description is empty!")
                    } else {
                        val isChange = chatUser.name != name.value || chatUser.avatar != avatar.value || chatUser.description != description.value
                        if (isChange) {
                            val savedPath = if (chatUser.avatar != avatar.value) {
                                copyFile2ConfigDir(avatar.value, AppBuildConfig.APP + File.separator + "avatar", "${id.value}")
                            } else {
                                chatUser.avatar
                            }
                            vm.changeUser(chatUser, savedPath, name.value, description.value)
                            screenNavigator.toast("Chat user info is changed!")
                        } else {
                            screenNavigator.toast("Chat user info is no change!")
                        }
                        screenNavigator.pop()
                    }
                })
        }
    }
}
