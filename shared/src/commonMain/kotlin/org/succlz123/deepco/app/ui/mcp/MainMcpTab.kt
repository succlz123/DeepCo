package org.succlz123.deepco.app.ui.mcp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_no
import deep_co.shared.generated.resources.ic_yes
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppAddButton
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppTable
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.base.TableTitleItem
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.mcp.MainMcpViewModel.Companion.MCP_SERVERS
import org.succlz123.lib.click.onClickUrl
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.viewModel

@Composable
fun MainMcpTab(modifier: Modifier = Modifier) {
    val mcpViewModel = viewModel {
        MainMcpViewModel()
    }
    val screenNavigator = LocalScreenNavigator.current
    MainTitleLayout(modifier, text = strings().tabMCP, topRightContent = {
    }) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).weight(1f).fillMaxWidth()) {
            Column() {
                Text(
                    strings().envInfo,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    val nodeJsVersion = mcpViewModel.nodeJsVersion.collectAsState().value
                    val pythonVersion = mcpViewModel.pythonVersion.collectAsState().value
                    val UVVersion = mcpViewModel.UVVersion.collectAsState().value
                    Column {
                        Row {
                            Text(
                                "Node.js",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                nodeJsVersion.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (nodeJsVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.error),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Text(
                                "brew install node",
                                color = if (nodeJsVersion.isNullOrEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primaryContainer,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        AppHorizontalDivider()
                        Spacer(modifier = Modifier.height(6.dp))
                        Row {
                            Text(
                                "Python",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                pythonVersion.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (pythonVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.error),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                            Text(
                                "brew install python3",
                                color = if (UVVersion.isNullOrEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primaryContainer,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        AppHorizontalDivider()
                        Spacer(modifier = Modifier.height(6.dp))
                        Row {
                            Text(
                                "UV",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                UVVersion.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (UVVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.error),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                            Text(
                                "brew install uv",
                                color = if (UVVersion.isNullOrEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primaryContainer,
                                fontSize = 12.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                AppHorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    strings().mcpServersCollection,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    for (n in MCP_SERVERS) {
                        Text(
                            n,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 6.dp).onClickUrl(n)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    strings().localMCPServers,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                var mcpList = mcpViewModel.mcpList.collectAsState().value

                val strings = strings()
                val titleList = remember(strings) {
                    buildList {
                        add(TableTitleItem(strings.name, 120, 0f))
                        add(TableTitleItem(strings.command, 120, 0f))
                        add(TableTitleItem(strings.args, 0, 1f))
                        add(TableTitleItem(strings.enable, 120, 0f))
                    }
                }
                AppTable(Modifier.fillMaxSize(), titleList, mcpList) { index, item ->
                    if (index != titleList.size - 1) {
                        return@AppTable false
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            AppImageIconButton(26.dp, MaterialTheme.colorScheme.error, Res.drawable.ic_remove) {
//                                showDialog.value = showDialog.value.copy(show = true, onPositiveClick = {
//                                    viewModel.remove((item as LLM).name)
//                                })
//                            }
//                            Spacer(modifier = Modifier.width(16.dp))
//                            AppImageIconButton(26.dp, MaterialTheme.colorScheme.primary, Res.drawable.ic_modify) {
//                            }
                        }
                        return@AppTable true
                    }
                }
            }
            AppAddButton(modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd), onClick = {
                screenNavigator.push(Manifest.MCPAddPopupScreen)
            })
        }
    }
}
