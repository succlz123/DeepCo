package org.succlz123.deepco.app.ui.mcp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.ui.mcp.MainMcpViewModel.Companion.MCP_SERVERS
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.onClickUrl
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.viewModel

@Composable
fun MainMcpTab(modifier: Modifier = Modifier) {
    val mcpViewModel = viewModel {
        MainMcpViewModel()
    }
    val screenNavigator = LocalScreenNavigator.current
    MainRightTitleLayout(modifier, text = "MCP", topRightContent = {
    }) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp).weight(1f).fillMaxWidth()) {
            Column() {
                Text(
                    "ENV Info",
                    color = ColorResource.primaryText,
                    style = MaterialTheme.typography.h4,
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
                                color = ColorResource.primaryText,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                nodeJsVersion.orEmpty(),
                                color = ColorResource.primaryText,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (nodeJsVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.red),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.green),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Text(
                                "brew install node",
                                color = if (nodeJsVersion.isNullOrEmpty()) ColorResource.red else ColorResource.green,
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
                                color = ColorResource.primaryText,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                pythonVersion.orEmpty(),
                                color = ColorResource.primaryText,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (pythonVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.red),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.green),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                            Text(
                                "brew install python3",
                                color = if (UVVersion.isNullOrEmpty()) ColorResource.red else ColorResource.green,
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
                                color = ColorResource.primaryText,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(80.dp)
                            )
                            Text(
                                UVVersion.orEmpty(),
                                color = ColorResource.primaryText,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 6.dp).width(260.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (UVVersion.isNullOrEmpty()) {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.red),
                                    painter = painterResource(resource = Res.drawable.ic_no),
                                )
                            } else {
                                Image(
                                    modifier = Modifier.size(18.dp),
                                    contentDescription = null,
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(ColorResource.green),
                                    painter = painterResource(resource = Res.drawable.ic_yes),
                                )
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                            Text(
                                "brew install uv",
                                color = if (UVVersion.isNullOrEmpty()) ColorResource.red else ColorResource.green,
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
                    "MCP Servers Collection",
                    color = ColorResource.primaryText,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    for (n in MCP_SERVERS) {
                        Text(
                            n,
                            color = ColorResource.theme,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 6.dp).onClickUrl(n)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Local MCP Servers",
                    color = ColorResource.primaryText,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                var mcpList = mcpViewModel.mcpList.collectAsState().value
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Row(
                            modifier = Modifier.background(ColorResource.background, shape = RoundedCornerShape(4.dp))
                        ) {
                            Text(
                                text = "Name", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Commend", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Args", modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Enable", modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.black, style = MaterialTheme.typography.h5
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                    itemsIndexed(mcpList, key = { index, item ->
                        item.toString()
                    }) { index, item ->
                        Row(
                            modifier = Modifier.noRippleClick {
                            }.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name.orEmpty(), modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.primaryText, style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = item.command.orEmpty(), modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp), color = ColorResource.primaryText, style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = item.args.orEmpty().joinToString(separator = " "),
                                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 6.dp),
                                color = ColorResource.primaryText,
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Row(modifier = Modifier.width(100.dp).padding(horizontal = 12.dp, vertical = 6.dp)) {
//                            AppButton(
//                                modifier = Modifier, contentPaddingValues = PaddingValues(
//                                    horizontal = 12.dp, vertical = 6.dp
//                                ), {
//                                }, {
//                                    Text("Enable", color = ColorResource.white, style = MaterialTheme.typography.body1)
//                                })
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        AppHorizontalDivider()
                    }
                }
            }
            AppAddButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = {
                screenNavigator.push(Manifest.MCPAddPopupScreen)
            })
        }
    }
}
