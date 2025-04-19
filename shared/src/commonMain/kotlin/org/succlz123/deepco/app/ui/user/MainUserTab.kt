package org.succlz123.deepco.app.ui.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_more_detail
import deep_co.shared.generated.resources.ic_remove
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppAddButton
import org.succlz123.deepco.app.base.AppDialogConfig
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppMessageDialog
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.ScreenArgs
import org.succlz123.lib.screen.viewmodel.globalViewModel
import org.succlz123.lib.screen.window.rememberIsWindowExpanded
import org.succlz123.lib.time.hhMMssSSS

@Composable
fun MainUserTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainUserViewModel() }
    var chatUserList = viewModel.chatUsers.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    MainTitleLayout(modifier, text = strings().chatUser, topRightContent = {}) {
        Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val showDialog = remember {
                mutableStateOf(AppDialogConfig.DEFAULT)
            }
            Column() {
                val isExpandedScreen = rememberIsWindowExpanded()
                val gridCellSize = remember(isExpandedScreen) {
                    if (isExpandedScreen) {
                        5
                    } else {
                        3
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridCellSize),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(chatUserList.sortedByDescending { it.createTime }) { index, item ->
                        Box(
                            modifier = modifier.clip(MaterialTheme.shapes.medium).border(BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainer), shape = MaterialTheme.shapes.medium)
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    UserAvatarView(Modifier, 48.dp, item.avatar)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            modifier = Modifier.noRippleClick {
                                            },
                                            text = item.name,
                                            style = MaterialTheme.typography.headlineMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(item.createTime.hhMMssSSS(), modifier = Modifier, color = MaterialTheme.colorScheme.tertiary, fontSize = 10.sp)
                                    }
                                    if (!item.isDefault) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Image(
                                            modifier = Modifier.size(16.dp).noRippleClick {
                                                showDialog.value = showDialog.value.copy(show = true, onPositiveClick = {
                                                    viewModel.remove(item)
                                                })
                                            },
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                                            painter = painterResource(resource = Res.drawable.ic_remove),
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                AppHorizontalDivider()
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.description, maxLines = 5, minLines = 5, modifier = Modifier,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                                    ) {
                                        Image(
                                            modifier = Modifier.size(16.dp).noRippleClick {
                                                screenNavigator.push(Manifest.ChatUserConfigPopupScreen, arguments = ScreenArgs.putValue("item", item))
                                            },
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                            painter = painterResource(resource = Res.drawable.ic_more_detail),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
            AppAddButton(modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)) {
                screenNavigator.push(Manifest.ChatUserConfigPopupScreen)
            }
            AppMessageDialog(strings().tips, strings().tipsRemoveUser, showDialog)
        }
    }
}