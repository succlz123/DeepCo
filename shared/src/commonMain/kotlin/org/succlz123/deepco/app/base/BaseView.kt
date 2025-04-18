package org.succlz123.deepco.app.base

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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_confirm
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator


@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp), color = ColorResource.theme, strokeWidth = 6.dp
        )
    }
}

@Composable
fun LoadingFailView(modifier: Modifier = Modifier, cancelClick: () -> Unit, okClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "数据加载失败!", fontSize = 26.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row {
            Button(
                colors = outlinedButtonColors(
                    backgroundColor = ColorResource.theme, contentColor = Color.White, disabledContentColor = Color.Transparent
                ), contentPadding = PaddingValues(
                    start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp
                ), onClick = {
                    cancelClick.invoke()
                }) {
                Text(text = "退出", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Button(
                colors = outlinedButtonColors(
                    backgroundColor = Color.Black, contentColor = Color.White, disabledContentColor = Color.Transparent
                ), contentPadding = PaddingValues(
                    start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp
                ), onClick = {
                    okClick.invoke()
                }) {
                Text(
                    text = "重试", fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun AppRefreshButton(modifier: Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, backgroundColor = Color.White, elevation = 12.dp) {
        Box(modifier = Modifier.padding(12.dp).noRippleClick(onClick)) {
            Icon(
                Icons.Sharp.Refresh, modifier = Modifier.size(32.dp).padding(2.dp), contentDescription = "Refresh", tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppAddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, backgroundColor = Color.White, elevation = 12.dp) {
        Box(modifier = Modifier.padding(12.dp).noRippleClick(onClick)) {
            Icon(
                Icons.Default.Add, modifier = Modifier.size(32.dp).padding(2.dp), contentDescription = "Refresh", tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppGo2TopButton(modifier: Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, backgroundColor = Color.White, elevation = 12.dp) {
        Box(modifier = Modifier.padding(12.dp).noRippleClick(onClick)) {
            Icon(
                Icons.Sharp.KeyboardArrowUp, modifier = Modifier.size(32.dp).padding(2.dp), contentDescription = "Go to top", tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppBackButton(modifier: Modifier = Modifier, tint: Color = Color.Black, onClick: (() -> Boolean)? = null) {
    val navigationScene = LocalScreenNavigator.current
    Icon(
        Icons.Sharp.ArrowBack, modifier = modifier.size(32.dp).noRippleClick {
            if (onClick?.invoke() == true) {
            } else {
                navigationScene.pop()
            }
        }, contentDescription = "Back", tint = tint
    )
}

@Composable
fun AppHorizontalDivider(modifier: Modifier = Modifier.fillMaxWidth()) {
    Spacer(modifier = modifier.height(1.dp).background(ColorResource.divider))
}

@Composable
fun AppVerticalDivider(modifier: Modifier = Modifier.fillMaxHeight()) {
    Spacer(modifier = modifier.width(1.dp).background(ColorResource.divider))
}


@Composable
fun AppButton(
    modifier: Modifier = Modifier, text: String,
    contentPaddingValues: PaddingValues = PaddingValues(
        start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
    ),
    onClick: () -> Unit,
) {
    AppButton(modifier, contentPaddingValues = contentPaddingValues, onClick = onClick, content = {
        Text(text, color = ColorResource.white, style = MaterialTheme.typography.body1)
    })
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier, contentPaddingValues: PaddingValues, onClick: () -> Unit, content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.background(ColorResource.theme, shape = RoundedCornerShape(4.dp)).padding(contentPaddingValues).noRippleClick {
            onClick.invoke()
        }) {
        content.invoke()
    }
}

@Composable
fun AppImageIconButton(
    size: Dp, color: Color, resource: DrawableResource, onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(color.copy(alpha = 0.1f), RoundedCornerShape(size)).border(BorderStroke(1.dp, color), RoundedCornerShape(size)).padding(horizontal = 12.dp, vertical = 12.dp).noRippleClick(onClick)
    ) {
        Image(
            modifier = Modifier.size(size / 3 * 2),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            painter = painterResource(resource = resource),
        )
    }
}

@Composable
fun AppImgButton(modifier: Modifier = Modifier, imageContent: @Composable () -> Unit, text: String, onClick: () -> Unit) {
    AppButton(
        modifier, contentPaddingValues = PaddingValues(
            start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
        ), onClick = onClick, content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                imageContent()
                Spacer(modifier = Modifier.width(6.dp))
                Text(text, color = ColorResource.white, style = MaterialTheme.typography.body1)
            }
        })
}

@Composable
fun AppConfirmButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    AppImgButton(modifier, imageContent = {
        Image(
            modifier = Modifier.size(14.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorResource.white),
            painter = painterResource(resource = Res.drawable.ic_confirm),
        )
    }, "Confirm", onClick)
}

@Composable
fun AsteriskText(text: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text, modifier = modifier, style = MaterialTheme.typography.h5
        )
        Text(
            text = "*", modifier = Modifier, style = MaterialTheme.typography.h5.copy(ColorResource.red)
        )
    }
}

@Composable
fun AppOutlineButton(
    modifier: Modifier, contentPaddingValues: PaddingValues = PaddingValues(
        start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
    ), onClick: () -> Unit, content: @Composable () -> Unit
) {
    OutlinedButton(
        modifier = modifier.padding(0.dp).defaultMinSize(minWidth = 1.dp, minHeight = 1.dp), onClick = onClick, border = BorderStroke(
            ButtonDefaults.OutlinedBorderSize, ColorResource.theme
        ), colors = outlinedButtonColors(
            backgroundColor = Color.Transparent, contentColor = ColorResource.theme, disabledContentColor = Color.Transparent
        ), contentPadding = contentPaddingValues
    ) {
        content.invoke()
    }
}

@Composable
fun AppSliderBar(modifier: Modifier, min: Float, max: Float, progress: Float, showText: Boolean = false, change: (Float) -> Unit) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        if (showText) {
            Text(min.toString(), color = ColorResource.primaryText, style = MaterialTheme.typography.body1)
            Spacer(Modifier.width(6.dp))
        }
        Slider(
            modifier = Modifier.weight(1f), value = progress, valueRange = min..max, onValueChange = {
                change(it)
            }, colors = SliderDefaults.colors(
                thumbColor = ColorResource.theme,
                inactiveTrackColor = ColorResource.border,
                activeTrackColor = ColorResource.theme,
            )
        )
        if (showText) {
            Spacer(Modifier.width(6.dp))
            Text(max.toString(), color = ColorResource.primaryText, style = MaterialTheme.typography.body1)
        }
    }
}