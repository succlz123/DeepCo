package org.succlz123.deepco.app.ui.prompt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.base.CustomExposedDropdownMenu
import org.succlz123.deepco.app.base.DropdownMenuDes
import org.succlz123.deepco.app.chat.prompt.PromptType
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun PromptAddDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainPromptViewModel()
    }
    Box(modifier = Modifier.fillMaxSize().noRippleClick {}, contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxSize().padding(128.dp)
                .align(Alignment.Center), elevation = 3.dp, backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(horizontal = 32.dp).verticalScroll(state = rememberScrollState())) {
                val name = remember { mutableStateOf("") }
                val description = remember { mutableStateOf("") }
                val selectedType = remember { mutableStateOf<PromptType>(PromptType.NORMAL) }
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(
                        modifier = Modifier,
                        text = "Add Prompt",
                        style = MaterialTheme.typography.h3,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.size(16.dp).noRippleClick {
                            screenNavigator.pop()
                        },
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_close),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Type", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomExposedDropdownMenu(listOf(PromptType.NORMAL, PromptType.ROLE).map {
                    DropdownMenuDes(it.name, it)
                }, "Select Type") { item ->
                    selectedType.value = item.tag as PromptType
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
                    scrollHeight = 250.dp,
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
                                val found = vm.prompt.value.find { it.name == name.value }
                                if (found != null) {
                                    screenNavigator.toast("Name is duplicate!")
                                } else {
                                    vm.add(selectedType.value, name.value, description.value)
                                    screenNavigator.pop()
                                }
                            }
                        })
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}