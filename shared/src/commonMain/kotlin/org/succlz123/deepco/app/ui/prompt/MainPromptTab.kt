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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_more_detail
import deep_co.shared.generated.resources.ic_remove
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.llm.role.PromptType
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.ScreenArgs
import org.succlz123.lib.screen.viewmodel.globalViewModel
import org.succlz123.lib.screen.window.rememberIsWindowExpanded

@Composable
fun MainPromptTab(modifier: Modifier = Modifier) {
    val viewModel = globalViewModel { MainPromptViewModel() }
    var promptList = viewModel.prompt.collectAsState().value
    val screenNavigator = LocalScreenNavigator.current
    MainRightTitleLayout(modifier, text = "Prompt", topRightContent = {
        AppButton(
            modifier = Modifier, text = "Add New Prompt", contentPaddingValues = PaddingValues(
                start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
            ), onClick = {
                screenNavigator.push(Manifest.PromptAddPopupScreen)
            })
    }) {
        Column(
            modifier = Modifier.padding(26.dp, 26.dp, 26.dp, 26.dp).fillMaxSize()
        ) {
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
                itemsIndexed(promptList.sortedByDescending { it.createTime }) { index, item ->
                    Box(
                        modifier = modifier.clip(RoundedCornerShape(8.dp)).border(BorderStroke(1.dp, ColorResource.black5), shape = RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        Column {
                            Row {
                                Text(
                                    modifier = Modifier.noRippleClickable {
                                    },
                                    text = item.name,
                                    style = MaterialTheme.typography.h5.copy(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    ),
                                    color = ColorResource.primaryText,
                                    maxLines = 1
                                )
                                if (!item.isDefault) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Image(
                                        modifier = Modifier.size(16.dp).noRippleClickable {
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
                                text = item.description, maxLines = 9, modifier = Modifier.height(160.dp),
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.body2.copy(
                                    color = ColorResource.secondaryText
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (item.type == PromptType.ROLE) {
                                    Box(modifier = Modifier.background(ColorResource.theme, RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text(
                                            modifier = Modifier,
                                            text = "Role",
                                            style = MaterialTheme.typography.caption,
                                            color = ColorResource.white,
                                            maxLines = 1
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                if (item.isDefault) {
                                    Box(modifier = Modifier.background(ColorResource.theme, RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text(
                                            modifier = Modifier,
                                            text = "Default",
                                            style = MaterialTheme.typography.caption,
                                            color = ColorResource.white,
                                            maxLines = 1
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier.background(ColorResource.theme, RoundedCornerShape(24.dp)).padding(horizontal = 4.dp, vertical = 4.dp)
                                ) {
                                    Image(
                                        modifier = Modifier.size(12.dp).noRippleClickable {
                                            screenNavigator.push(Manifest.PromptDetailPopupScreen, arguments = ScreenArgs.putValue("item", item))
                                        },
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(ColorResource.white),
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
    }
}