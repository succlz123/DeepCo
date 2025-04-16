package org.succlz123.deepco.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.lib.common.isDesktop
import org.succlz123.lib.screen.LocalScreenWindowSizeOwner
import org.succlz123.lib.screen.window.ScreenWindowSizeClass

private val DarkColorPalette = darkColors(
    background = Color(0xFF2B2B2B),
    surface = Color(0xFF3C3F41)
)

private val LightColorPalette = lightColors(
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF)
)

val LocalAppDimens = staticCompositionLocalOf { expandedDimens }

val AppDialogPadding
    @Composable
    get() = LocalAppDialogPadding.current

val LocalAppDialogPadding = staticCompositionLocalOf { 16.dp }

val ContentPadding
    @Composable
    get() = LocalContentPadding.current

val LocalContentPadding = staticCompositionLocalOf { 16.dp }

class AppDimens(val listContentPadding: Dp)

val compactDimens = AppDimens(listContentPadding = 12.dp)

val expandedDimens = AppDimens(listContentPadding = 16.dp)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val typography = Typography(
        h1 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 20.sp
        ), h2 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 18.sp
        ), h3 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 16.sp
        ), h4 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 14.sp
        ), h5 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 12.sp
        ), h6 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 10.sp
        ), subtitle1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = ColorResource.primaryText
        ), subtitle2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = ColorResource.secondaryText
        ), body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = ColorResource.primaryText
        ), body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = ColorResource.secondaryText
        ), button = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.W500, fontSize = 12.sp
        ), caption = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 10.sp
        ), overline = TextStyle(
            fontWeight = FontWeight.Normal, fontSize = 8.sp, letterSpacing = 1.5.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(8.dp), medium = RoundedCornerShape(12.dp), large = RoundedCornerShape(16.dp)
    )
    val windowSizeOwner = LocalScreenWindowSizeOwner.current
    val windowSizeClass = remember {
        windowSizeOwner.getWindowHolder().sizeClass.value
    }
    val dimens = when (windowSizeClass) {
        ScreenWindowSizeClass.Compact -> {
            compactDimens
        }

        ScreenWindowSizeClass.Medium -> {
            compactDimens
        }

        ScreenWindowSizeClass.Expanded -> expandedDimens
    }
    val dialogPadding = remember {
        if (isDesktop()) {
            48.dp
        } else {
            16.dp
        }
    }
    val contentPadding = remember {
        if (isDesktop()) {
            32.dp
        } else {
            16.dp
        }
    }
    CompositionLocalProvider(
        LocalAppDimens provides dimens,
        LocalAppDialogPadding provides dialogPadding,
        LocalContentPadding provides contentPadding
    ) {
        MaterialTheme(
            colors = colors, typography = typography, shapes = shapes, content = content
        )
    }
}
