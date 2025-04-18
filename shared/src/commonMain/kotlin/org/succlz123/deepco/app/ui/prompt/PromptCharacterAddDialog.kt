package org.succlz123.deepco.app.ui.prompt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_my
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.character.TavernCardV2
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.user.UserAvatarView
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.file.choseImgFile
import org.succlz123.lib.image.AsyncImageUrlMultiPlatform
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.LocalScreenRecord
import org.succlz123.lib.screen.value
import org.succlz123.lib.screen.viewmodel.globalViewModel
import java.io.File

class CharacterImport(val cardV2: TavernCardV2, val imgPath: String? = null)

@Composable
fun PromptCharacterAddDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val characterImport = LocalScreenRecord.current.arguments.value<CharacterImport>("item")
    if (characterImport == null) {
        screenNavigator.toast("Import Info is Null!")
        screenNavigator.pop()
        return
    }
    val tavernCardV2 = remember { mutableStateOf<TavernCardV2>(characterImport.cardV2) }
    val vm = globalViewModel {
        MainPromptViewModel()
    }
    BaseDialogCardWithTitleColumnScroll("Character Detail") {
        val avatar = remember { mutableStateOf(characterImport.imgPath.orEmpty()) }
        val tags = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.tags.orEmpty()) }
        val creator = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.creator.orEmpty()) }
        val id = remember { mutableStateOf(System.currentTimeMillis()) }
        val name = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.name.orEmpty()) }
        val description = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.description.orEmpty()) }
        val creatorNotes = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.creator_notes.orEmpty()) }
        val greetingMessage = remember(tavernCardV2.value) { mutableStateOf(tavernCardV2.value.data.first_mes.orEmpty()) }
        Text(
            text = "Avatar", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Box(modifier = Modifier.fillMaxWidth().noRippleClick {
                val choseFile = choseImgFile()
                if (choseFile != null) {
                    avatar.value = choseFile
                }
            }) {
                UserAvatarView(Modifier.align(Alignment.Center), 82.dp, avatar.value)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.height(100.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    tags.value.forEach {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier.background(ColorResource.theme, shape = RoundedCornerShape(4.dp))
                                .padding(16.dp, 4.dp)
                        ) {
                            Text(it, color = ColorResource.white, style = MaterialTheme.typography.h4)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Created By", modifier = Modifier, color = ColorResource.primaryText, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(creator.value, modifier = Modifier, color = ColorResource.secondaryText, fontSize = 16.sp)
                }
            }
        }
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
            }, scrollHeight = 200.dp, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Creator Notes", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            creatorNotes.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = false,
            maxLines = 10,
            onValueChange = {
                creatorNotes.value = it
            }, scrollHeight = 200.dp, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Greeting Message", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            greetingMessage.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = false,
            maxLines = 10,
            onValueChange = {
                greetingMessage.value = it
            }, scrollHeight = 200.dp, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            AppButton(
                modifier = Modifier.align(Alignment.BottomEnd), text = "Save", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
                    val found = vm.prompt.value.find { it.name == name.value }
                    if (found != null) {
                        screenNavigator.toast("Card is duplicate!")
                    } else {
                        val savedPath = if (avatar.value.isNotEmpty()) {
                            org.succlz123.lib.setting.copyFile2ConfigDir(avatar.value, AppBuildConfig.APP + File.separator + "avatar", "tavern_card_v2_${id.value}")
                        } else {
                            ""
                        }
                        vm.addTavernCard(id.value, savedPath, tavernCardV2.value)
                        screenNavigator.pop()
                    }
                })
        }
    }
}