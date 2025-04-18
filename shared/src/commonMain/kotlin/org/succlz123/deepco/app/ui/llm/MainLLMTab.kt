package org.succlz123.deepco.app.ui.llm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_modify
import deep_co.shared.generated.resources.ic_remove
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppAddButton
import org.succlz123.deepco.app.base.AppDialogConfig
import org.succlz123.deepco.app.base.AppImageIconButton
import org.succlz123.deepco.app.base.AppMessageDialog
import org.succlz123.deepco.app.base.AppTable
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.base.TableTitleItem
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.ScreenArgs
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun MainLLMTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainLLMViewModel() }
    var llmConfigList = viewModel.llmConfigs.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    MainRightTitleLayout(modifier, text = "My LLM", topRightContent = {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(ColorResource.orangeLight, RoundedCornerShape(26.dp))
                .border(BorderStroke(1.dp, ColorResource.orange), RoundedCornerShape(26.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text("Current support DeepSeek / Grok / Gemini", modifier = Modifier, style = MaterialTheme.typography.caption.copy(color = ColorResource.orange))
        }
    }) {
        Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val showDialog = remember {
                mutableStateOf(AppDialogConfig.DEFAULT)
            }
            Column {
                val titleList = remember {
                    buildList {
                        add(TableTitleItem("Provider", 120, 1f))
                        add(TableTitleItem("Name", 120, 1f))
                        add(TableTitleItem("BaseUrl", 0, 1f))
                        add(TableTitleItem("Model", 0, 1f))
                        add(TableTitleItem("API Key", 120, 0f))
                        add(TableTitleItem("Created Time", 120, 0f))
                        add(TableTitleItem("Operation", 120, 0f))
                    }
                }
                AppTable(Modifier.fillMaxSize(), titleList, llmConfigList) { index, item ->
                    if (index != titleList.size - 1) {
                        return@AppTable false
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppImageIconButton(26.dp, ColorResource.error, Res.drawable.ic_remove) {
                                showDialog.value = showDialog.value.copy(show = true, onPositiveClick = {
                                    viewModel.remove((item as LLM).name)
                                })
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            AppImageIconButton(26.dp, ColorResource.theme, Res.drawable.ic_modify) {
                                screenNavigator.push(Manifest.LLMConfigPopupScreen, arguments = ScreenArgs.putValue("llm", item))
                            }
                        }
                        return@AppTable true
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
            AppAddButton(modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)) {
                screenNavigator.push(Manifest.LLMConfigPopupScreen)
            }
            AppMessageDialog("Tips", "Are you sure to remove this configuration？", showDialog)
        }
    }
}