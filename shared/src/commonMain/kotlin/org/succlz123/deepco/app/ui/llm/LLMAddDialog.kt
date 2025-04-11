package org.succlz123.deepco.app.ui.llm

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
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.base.CustomExposedDropdownMenu
import org.succlz123.deepco.app.base.DropdownMenuDes
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel.Companion.DEFAULT_LLM
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun LLMAddDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainLLMViewModel()
    }
    Box(modifier = Modifier.fillMaxSize().noRippleClickable {}, contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxSize().padding(128.dp)
                .align(Alignment.Center), elevation = 3.dp, backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(horizontal = 32.dp).verticalScroll(state = rememberScrollState())) {
                val selectedProvider = remember { mutableStateOf<LLM?>(null) }
                val name = remember { mutableStateOf("") }
                val baseUrl = remember { mutableStateOf("") }
                val apiKey = remember { mutableStateOf("") }
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(
                        modifier = Modifier,
                        text = "Configure LLM",
                        style = MaterialTheme.typography.h3,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.size(16.dp).noRippleClickable {
                            screenNavigator.pop()
                        },
                        contentDescription = null,
                        painter = org.jetbrains.compose.resources.painterResource(resource = Res.drawable.ic_close),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Provider", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomExposedDropdownMenu(DEFAULT_LLM.map {
                    DropdownMenuDes(it.provider.orEmpty(), it)
                }, "Select Provider") { item ->
                    selectedProvider.value = item.tag as LLM
                    baseUrl.value = selectedProvider.value?.baseUrl.orEmpty()
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
                    onValueChange = {
                        name.value = it
                    }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = "Base API Url", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                CustomEdit(
//                    baseUrl.value,
//                    textStyle = TextStyle.Default.copy(fontSize = 14.sp),
//                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                    onValueChange = {
//                        baseUrl.value = it
//                    }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
//                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "API Key", modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomEdit(
                    apiKey.value,
                    textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        apiKey.value = it
                    }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    AppButton(
                        modifier = Modifier.align(Alignment.BottomEnd), text = "Save", contentPaddingValues = PaddingValues(
                            start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                        ), onClick = {
                            vm.add(selectedProvider.value?.provider.orEmpty(), name.value, apiKey.value, baseUrl.value)
                            screenNavigator.pop()
                        })
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}