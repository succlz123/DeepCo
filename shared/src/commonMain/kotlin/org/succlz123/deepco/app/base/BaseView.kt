package org.succlz123.deepco.app.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.screen.LocalScreenNavigator

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius.toPx()))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)


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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "数据加载失败!", fontSize = 26.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))
        Row {
            Button(
                colors = outlinedButtonColors(
                    backgroundColor = ColorResource.theme,
                    contentColor = Color.White,
                    disabledContentColor = Color.Transparent
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
        Box(modifier = Modifier.padding(12.dp).noRippleClickable(onClick)) {
            Icon(
                Icons.Sharp.Refresh,
                modifier = Modifier.size(32.dp).padding(2.dp),
                contentDescription = "Refresh",
                tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppAddButton(modifier: Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, backgroundColor = Color.White, elevation = 12.dp) {
        Box(modifier = Modifier.padding(12.dp).noRippleClickable(onClick)) {
            Icon(
                Icons.Default.Add,
                modifier = Modifier.size(32.dp).padding(2.dp),
                contentDescription = "Refresh",
                tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppGo2TopButton(modifier: Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, backgroundColor = Color.White, elevation = 12.dp) {
        Box(modifier = Modifier.padding(12.dp).noRippleClickable(onClick)) {
            Icon(
                Icons.Sharp.KeyboardArrowUp,
                modifier = Modifier.size(32.dp).padding(2.dp),
                contentDescription = "Go to top",
                tint = ColorResource.theme
            )
        }
    }
}

@Composable
fun AppBackButton(modifier: Modifier = Modifier, tint: Color = Color.Black, onClick: (() -> Boolean)? = null) {
    val navigationScene = LocalScreenNavigator.current
    Icon(
        Icons.Sharp.ArrowBack, modifier = modifier.size(32.dp).noRippleClickable {
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
    modifier: Modifier = Modifier, contentPaddingValues: PaddingValues = PaddingValues(
        start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
    ), onClick: () -> Unit, content: @Composable () -> Unit
) {
    Button(
        modifier = modifier.padding(0.dp).defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        colors = outlinedButtonColors(
            backgroundColor = ColorResource.theme, contentColor = Color.White, disabledContentColor = Color.Transparent
        ),
        contentPadding = contentPaddingValues, onClick = onClick
    ) {
        content.invoke()
    }
}

//Box(
//modifier = Modifier.background(ColorResource.theme, shape = RoundedCornerShape(4.dp))
//.padding(horizontal = 16.dp, vertical = 10.dp).noRippleClickable {
//}
//) {
//    Text("New Chat", color = ColorResource.white, style = MaterialTheme.typography.body1)
//}

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
            backgroundColor = Color.Transparent,
            contentColor = ColorResource.theme,
            disabledContentColor = Color.Transparent
        ), contentPadding = contentPaddingValues
    ) {
        content.invoke()
    }
}