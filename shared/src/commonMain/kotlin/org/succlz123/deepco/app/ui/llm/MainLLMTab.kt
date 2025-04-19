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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.base.TableTitleItem
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.ScreenArgs
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun MainLLMTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainLLMViewModel() }
    var llmConfigList = viewModel.llmConfigs.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    MainTitleLayout(modifier, text = strings().myLLM, topRightContent = {
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(ColorResource.orangeLight, RoundedCornerShape(26.dp))
                .border(BorderStroke(1.dp, ColorResource.orange), RoundedCornerShape(26.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(strings().currentSupportLLM, modifier = Modifier, style = MaterialTheme.typography.labelSmall.copy(color = ColorResource.orange))
        }
    }) {
        Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val showDialog = remember {
                mutableStateOf(AppDialogConfig.DEFAULT)
            }
            Column {
                val strings = strings()
                val titleList = remember(strings) {
                    buildList {
                        add(TableTitleItem(strings.provider, 120, 0f))
                        add(TableTitleItem(strings.name, 120, 0f))
                        add(TableTitleItem(strings.model, 0, 1f))
                        add(TableTitleItem(strings.baseAPIUrl, 0, 1f))
                        add(TableTitleItem(strings.apiKey, 120, 0f))
                        add(TableTitleItem(strings.createdTime, 120, 0f))
                        add(TableTitleItem(strings.operation, 120, 0f))
                    }
                }
                AppTable(Modifier.fillMaxSize(), titleList, llmConfigList) { index, item ->
                    if (index != titleList.size - 1) {
                        return@AppTable false
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppImageIconButton(26.dp, MaterialTheme.colorScheme.error, Res.drawable.ic_remove) {
                                showDialog.value = showDialog.value.copy(show = true, onPositiveClick = {
                                    viewModel.remove((item as LLM).name)
                                })
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            AppImageIconButton(26.dp, MaterialTheme.colorScheme.primary, Res.drawable.ic_modify) {
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
            AppMessageDialog(strings().tips, strings().tipsRemoveLLM, showDialog)
        }
    }
}