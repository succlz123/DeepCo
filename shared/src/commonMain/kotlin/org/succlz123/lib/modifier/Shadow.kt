package org.succlz123.lib.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

expect fun Modifier.shadow(
    color: Color = Color(0x47000000),
    borderRadius: Dp = 4.dp,
    blurRadius: Dp = 12.dp,
    offsetY: Dp = 6.dp,
    offsetX: Dp = 1.dp,
    spread: Dp = 0.dp,
    modifier: Modifier = Modifier
): Modifier
