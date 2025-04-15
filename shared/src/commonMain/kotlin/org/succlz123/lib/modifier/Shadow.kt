package org.succlz123.lib.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

expect fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
): Modifier