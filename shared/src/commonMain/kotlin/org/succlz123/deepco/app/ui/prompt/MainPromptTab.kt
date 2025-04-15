package org.succlz123.deepco.app.ui.prompt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_more_detail
import deep_co.shared.generated.resources.ic_remove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.character.Png
import org.succlz123.deepco.app.character.TavernCardV2
import org.succlz123.deepco.app.chat.prompt.PromptType
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.theme.ColorResource
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
    MainRightTitleLayout(modifier, text = "Prompt", topRightContent = {
        Row {
            AppButton(
                modifier = Modifier, text = "Import Tavern V2 Card(PNG/JSON)", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
                    scope.launch(Dispatchers.IO) {
                        val choseFile = org.succlz123.lib.file.choseFile(listOf(".png", ".json"))
                        if (choseFile == null) {
                            withContext(Dispatchers.Default) {
                                screenNavigator.toast("Please chose a file to import!")
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
                                        screenNavigator.toast("Please chose the Tavern Card file!")
                                    }
                                } else {
                                    screenNavigator.push(
                                        Manifest.PromptCharacterAddPopupScreen,
                                        arguments = ScreenArgs.putValue("item", CharacterImport(card, choseFile))
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
                                    screenNavigator.toast("Please chose the Tavern Card file!")
                                }
                            } else {
                                withContext(Dispatchers.Default) {
                                    screenNavigator.push(
                                        Manifest.PromptCharacterAddPopupScreen,
                                        arguments = ScreenArgs.putValue("item", CharacterImport(card))
                                    )
                                }
                            }
                        } else {
                            withContext(Dispatchers.Default) {
                                screenNavigator.toast("Please chose the png or json file!")
                            }
                        }
                    }
                })
            Spacer(modifier = Modifier.width(16.dp))
            AppButton(
                modifier = Modifier, text = "Add New Prompt", contentPaddingValues = PaddingValues(
                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                ), onClick = {
                    screenNavigator.push(Manifest.PromptAddPopupScreen)
                })
        }
    }) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Text(text = "Tavern Card Websites", modifier = Modifier, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(6.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(CHARACTER) { index, item ->
                    Text(text = item, modifier = Modifier.onClickUrl(item), color = ColorResource.theme, style = MaterialTheme.typography.body1)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Prompt List", modifier = Modifier, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(6.dp))
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
                    val textColor = remember(item.avatar) {
                        if (!item.avatar.isNullOrEmpty()) {
                            ColorResource.white
                        } else {
                            ColorResource.primaryText
                        }
                    }
                    val textThemeColor = remember(item.avatar) {
                        if (!item.avatar.isNullOrEmpty()) {
                            ColorResource.white
                        } else {
                            ColorResource.theme
                        }
                    }
                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).height(200.dp).border(BorderStroke(1.dp, ColorResource.black5), shape = RoundedCornerShape(8.dp))) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            if (!item.avatar.isNullOrEmpty()) {
                                AsyncImageUrlMultiPlatform(modifier = Modifier.fillMaxSize(), item.avatar)
                                Box(modifier = Modifier.fillMaxSize().background(ColorResource.black.copy(alpha = 0.2f)))
                            }
                        }
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Row {
                                Text(
                                    modifier = Modifier.noRippleClick {}, text = item.name, style = MaterialTheme.typography.h5.copy(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    ), color = textColor, maxLines = 1
                                )
                                if (!item.isDefault) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Image(
                                        modifier = Modifier.size(16.dp).noRippleClick {
                                            viewModel.remove(item)
                                        },
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(ColorResource.error),
                                        painter = painterResource(resource = Res.drawable.ic_remove),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            AppHorizontalDivider()
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.description, modifier = Modifier.weight(1f).fillMaxWidth(), maxLines = 7, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.body2.copy(
                                    color = textColor
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                if (item.type != PromptType.NORMAL) {
                                    Text(
                                        modifier = Modifier, text = item.type.name, style = MaterialTheme.typography.caption, color = textThemeColor, maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                if (item.isDefault) {
                                    Text(
                                        modifier = Modifier, text = "Default", style = MaterialTheme.typography.caption, color = textThemeColor, maxLines = 1
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
    }
}