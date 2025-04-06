package org.succlz123.deepco.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.lib.screen.LocalScreenWindowSizeOwner
import org.succlz123.lib.screen.window.ScreenWindowSizeClass

private val DarkColorPalette = darkColors(
    primary = ColorResource.theme, secondary = Color.LightGray
)

private val LightColorPalette = lightColors(
    primary = ColorResource.theme, secondary = Color.Black
)

val LocalAppDimens = staticCompositionLocalOf { expandedDimens }

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
        ), body1 = TextStyle(
            fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp
        ), body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = ColorResource.subText
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
    CompositionLocalProvider(
        LocalAppDimens provides dimens
    ) {
        MaterialTheme(
            colors = colors, typography = typography, shapes = shapes, content = content
        )
    }
}

class AppDimens(
    val listContentPadding: Dp
)

val compactDimens = AppDimens(
    listContentPadding = 12.dp,
)

val expandedDimens = AppDimens(
    listContentPadding = 16.dp,
)


@Immutable
data class Theme(
    val materialColors: Colors,
    val colors: ExtendedColors,
    val code: CodeStyle
) {

    @Immutable
    class ExtendedColors(
        val codeGuide: Color
    )

    @Immutable
    data class CodeStyle(
        val simple: SpanStyle,
        val value: SpanStyle,
        val keyword: SpanStyle,
        val punctuation: SpanStyle,
        val annotation: SpanStyle,
        val comment: SpanStyle
    )

    companion object {

        val dark = Theme(
            materialColors = darkColors(
                background = Color(0xFF2B2B2B),
                surface = Color(0xFF3C3F41)
            ),
            colors = ExtendedColors(
                codeGuide = Color(0xFF4E5254)
            ),
            code = CodeStyle(
                simple = SpanStyle(Color(0xFFA9B7C6)),
                value = SpanStyle(Color(0xFF6897BB)),
                keyword = SpanStyle(Color(0xFFCC7832)),
                punctuation = SpanStyle(Color(0xFFA1C17E)),
                annotation = SpanStyle(Color(0xFFBBB529)),
                comment = SpanStyle(Color(0xFF808080))
            )
        )

        val light = Theme(
            materialColors = lightColors(
                background = Color(0xFFF5F5F5),
                surface = Color(0xFFFFFFFF)
            ),
            colors = ExtendedColors(
                codeGuide = Color(0xFF8E9294)
            ),
            code = CodeStyle(
                simple = SpanStyle(Color(0xFF000000)),
                value = SpanStyle(Color(0xFF4A86E8)),
                keyword = SpanStyle(Color(0xFF000080)),
                punctuation = SpanStyle(Color(0xFFA1A1A1)),
                annotation = SpanStyle(Color(0xFFBBB529)),
                comment = SpanStyle(Color(0xFF808080))
            )
        )
    }
}

val LocalTheme = staticCompositionLocalOf { Theme.dark }

val AppTheme
    @Composable
    get() = LocalTheme.current
