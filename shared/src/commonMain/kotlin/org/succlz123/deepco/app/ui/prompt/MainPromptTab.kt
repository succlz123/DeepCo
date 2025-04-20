package org.succlz123.deepco.app.ui.prompt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_more_detail
import deep_co.shared.generated.resources.ic_remove
import deep_co.shared.generated.resources.logo_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppAddButton
import org.succlz123.deepco.app.base.AppDialogConfig
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppMessageDialog
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.character.Png
import org.succlz123.deepco.app.character.TavernCardV2
import org.succlz123.deepco.app.chat.prompt.PromptType
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.ui.prompt.MainPromptViewModel.Companion.CHARACTER
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.click.onClickUrl
import org.succlz123.lib.image.AsyncImageUrlMultiPlatform
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.ScreenArgs
import org.succlz123.lib.screen.viewmodel.globalViewModel
import org.succlz123.lib.screen.window.rememberIsWindowExpanded
import java.io.File

@Composable
fun MainPromptTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainPromptViewModel() }
    var promptList = viewModel.prompt.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    val scope = rememberCoroutineScope()
    val strings = strings()
    MainTitleLayout(modifier, text = strings.promptTitle, topRightContent = {}) {
        Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val showDialog = remember {
                mutableStateOf(AppDialogConfig.DEFAULT)
            }
            Column() {
                Text(text = strings.tavernCardWebsite, modifier = Modifier, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 200.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(CHARACTER) { index, item ->
                        Text(text = item, modifier = Modifier.onClickUrl(item), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = strings.promptList, modifier = Modifier, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                val isExpandedScreen = rememberIsWindowExpanded()
                val gridCellSize = remember(isExpandedScreen) {
                    if (isExpandedScreen) {
                        5
                    } else {
                        3
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridCellSize), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(promptList.sortedByDescending { it.createTime }) { index, item ->
                        val colorScheme = MaterialTheme.colorScheme
                        val textColor = remember(item.avatar) {
                            if (!item.avatar.isNullOrEmpty()) {
                                Color.White
                            } else {
                                colorScheme.onSurface
                            }
                        }
                        val textThemeColor = remember(item.avatar) {
                            if (!item.avatar.isNullOrEmpty()) {
                                Color.White
                            } else {
                                colorScheme.onSurface
                            }
                        }
                        Box(
                            modifier = Modifier.clip(MaterialTheme.shapes.large).height(200.dp)
                                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), shape = MaterialTheme.shapes.large)
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                if (!item.avatar.isNullOrEmpty()) {
                                    AsyncImageUrlMultiPlatform(modifier = Modifier.fillMaxSize(), item.avatar)
                                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)))
                                }
                            }
                            Column(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                            ) {
                                Row {
                                    Text(
                                        modifier = Modifier.noRippleClick {}, text = item.name, style = MaterialTheme.typography.titleMedium, color = textColor, maxLines = 1
                                    )
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
                                    text = item.description, modifier = Modifier.weight(1f).fillMaxWidth(), maxLines = 7, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium.copy(
                                        color = textColor
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    if (item.type != PromptType.NORMAL) {
                                        Text(
                                            modifier = Modifier, text = item.type.name, style = MaterialTheme.typography.labelMedium, color = textThemeColor, maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    if (item.isDefault) {
                                        Text(
                                            modifier = Modifier, text = strings.default, style = MaterialTheme.typography.labelMedium, color = textThemeColor, maxLines = 1
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Image(
                                        modifier = Modifier.size(16.dp).noRippleClick {
                                            screenNavigator.push(Manifest.PromptDetailPopupScreen, arguments = ScreenArgs.putValue("item", item))
                                        },
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(textThemeColor),
                                        painter = painterResource(resource = Res.drawable.ic_more_detail),
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd), horizontalAlignment = Alignment.End) {
                Row(modifier = Modifier.noRippleClick {
                    scope.launch(Dispatchers.IO) {
                        val choseFile = org.succlz123.lib.file.choseFile(listOf(".png", ".json"))
                        if (choseFile == null) {
                            withContext(Dispatchers.Default) {
                                screenNavigator.toast(strings.errorImportTavernV2CardNoneFile)
                            }
                        } else if (choseFile.endsWith("png")) {
                            val card = try {
                                appJson.decodeFromString<TavernCardV2>(Png.parse(File(choseFile).readBytes()))
                            } catch (e: Exception) {
                                null
                            }
                            withContext(Dispatchers.Default) {
                                if (card == null) {
                                    withContext(Dispatchers.Default) {
                                        screenNavigator.toast(strings.errorImportTavernV2CardContentInvalidation)
                                    }
                                } else {
                                    screenNavigator.push(
                                        Manifest.PromptCharacterAddPopupScreen, arguments = ScreenArgs.putValue("item", CharacterImport(card, choseFile))
                                    )
                                }
                            }
                        } else if (choseFile.endsWith("json")) {
                            val card = try {
                                appJson.decodeFromString<TavernCardV2>(File(choseFile).readText())
                            } catch (e: Exception) {
                                null
                            }
                            if (card == null) {
                                withContext(Dispatchers.Default) {
                                    screenNavigator.toast(strings.errorImportTavernV2CardContentInvalidation)
                                }
                            } else {
                                withContext(Dispatchers.Default) {
                                    screenNavigator.push(
                                        Manifest.PromptCharacterAddPopupScreen, arguments = ScreenArgs.putValue("item", CharacterImport(card))
                                    )
                                }
                            }
                        } else {
                            withContext(Dispatchers.Default) {
                                screenNavigator.toast(strings.errorImportTavernV2CardFileInvalidation)
                            }
                        }
                    }
                }, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium).padding(8.dp, 3.dp),
                        text = strings.importTavernV2Card,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier.width(12.dp))
                    Card(
                        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Box(modifier = Modifier.padding(12.dp)) {
                            Image(
                                modifier = Modifier.size(32.dp),
                                contentDescription = null,
                                painter = painterResource(resource = Res.drawable.logo_st),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                AppAddButton() {
                    screenNavigator.push(Manifest.PromptAddPopupScreen)
                }
            }
            AppMessageDialog(strings.tips, strings.tipsRemovePrompt, showDialog)
        }
    }
}