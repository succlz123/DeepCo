package org.succlz123.deepco.app.ui.llm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun MainLLMTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainLLMViewModel() }
    var llmConfigList = viewModel.llmConfigs.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    MainRightTitleLayout(modifier, text = "My LLM", topRightContent = {
        AppButton(
            modifier = Modifier, text = "Add New LLM", contentPaddingValues = PaddingValues(
                start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
            ), onClick = {
                screenNavigator.push(Manifest.LLMAddPopupScreen)
            })
    }) {
        Column(
            modifier = Modifier.padding(26.dp, 26.dp, 26.dp, 26.dp).fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Row(
                        modifier = Modifier.background(ColorResource.background, shape = RoundedCornerShape(4.dp))
                    ) {
                        Text(
                            text = "Provider", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Name", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Model", modifier = Modifier.width(200.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "API Key", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Create Time", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Operation", modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
                itemsIndexed(llmConfigList, key = { index, item ->
                    item.toString()
                }) { index, item ->
                    Row(
                        modifier = Modifier.noRippleClickable {
                        }.padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.provider.orEmpty(), modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.primaryText, style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.name.orEmpty(), modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.primaryText, style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.modes.joinToString(separator = "\n"),
                            modifier = Modifier.width(200.dp).padding(horizontal = 12.dp, vertical = 6.dp),
                            color = ColorResource.primaryText,
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.getMaskedKey(), modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.primaryText, style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.createDataFmt(),
                            modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp),
                            maxLines = 1,
                            color = ColorResource.primaryText,
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                            AppButton(
                                modifier = Modifier, text = "Delete", contentPaddingValues = PaddingValues(
                                    horizontal = 12.dp, vertical = 6.dp
                                ), onClick = {
                                    viewModel.remove(item.name.orEmpty())
                                })
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    AppHorizontalDivider()
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}