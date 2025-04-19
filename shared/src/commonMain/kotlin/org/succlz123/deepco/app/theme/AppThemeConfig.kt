package org.succlz123.deepco.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import org.succlz123.deepco.app.json.appJson
import org.succlz123.deepco.app.ui.setting.MainSettingViewModel.Companion.getLocaleAppTheme
import org.succlz123.deepco.app.ui.setting.MainSettingViewModel.Companion.getSettingLocal
import org.succlz123.lib.common.isDesktop
import org.succlz123.lib.screen.LocalScreenWindowSizeOwner
import org.succlz123.lib.screen.window.ScreenWindowSizeClass

val LocalAppDimens = staticCompositionLocalOf { expandedDimens }

val AppDialogPadding
    @Composable get() = LocalAppDialogPadding.current

val LocalAppDialogPadding = staticCompositionLocalOf { 16.dp }

val ContentPadding
    @Composable get() = LocalContentPadding.current

val LocalContentPadding = staticCompositionLocalOf { 16.dp }

class AppDimens(val listContentPadding: Dp)

val compactDimens = AppDimens(listContentPadding = 12.dp)

val expandedDimens = AppDimens(listContentPadding = 16.dp)

enum class AppThemeConfig {
    Green, Blue, Red, Yellow
}

private fun String.toColor(): Color {
    val hex = when {
        startsWith("0x") || startsWith("0X") -> substring(2)
        startsWith("#") -> substring(1)
        else -> return Color.Black
    }
    return try {
        Color(hex.toLong(16) or 0xFF000000)
    } catch (e: NumberFormatException) {
        Color.Black
    }
}

val appThemeConfig = mutableStateOf<AppThemeConfig>(getLocaleAppTheme())

fun appTheme2(name: String) {
    appThemeConfig.value = when (name) {
        AppThemeConfig.Blue.name -> AppThemeConfig.Blue
        AppThemeConfig.Green.name -> AppThemeConfig.Green
        AppThemeConfig.Red.name -> AppThemeConfig.Red
        AppThemeConfig.Yellow.name -> AppThemeConfig.Yellow
        else -> AppThemeConfig.Blue
    }
}

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    var schemes = remember {
        mutableStateOf(Schemes())
    }.value

    val colors = remember {
        mutableStateOf<ColorScheme>(lightColorScheme())
    }
    val typography = remember(colors.value) {
        mutableStateOf(
            Typography(
                displayLarge = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = colors.value.onSurface
                ),
                displayMedium = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = colors.value.onSurface
                ),
                displaySmall = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = colors.value.onSurface
                ),
                headlineLarge = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = colors.value.onSurface
                ),
                headlineMedium = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = colors.value.primary
                ),
                headlineSmall = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = colors.value.secondary
                ),
                titleLarge = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = colors.value.onSurface
                ),
                titleMedium = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp, color = colors.value.primary
                ),
                titleSmall = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp, color = colors.value.secondary
                ),
                bodyLarge = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = colors.value.onSurface
                ),
                bodyMedium = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp, color = colors.value.primary
                ),
                bodySmall = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.W500, fontSize = 12.sp, color = colors.value.secondary
                ),
                labelLarge = TextStyle(
                    fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp, color = colors.value.onSurface
                ),
                labelMedium = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal, fontSize = 12.sp, color = colors.value.primary, letterSpacing = 1.5.sp
                ),
                labelSmall = TextStyle(
                    fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal, fontSize = 10.sp, color = colors.value.secondary, letterSpacing = 1.5.sp
                )
            )
        )
    }
    LaunchedEffect(appThemeConfig.value) {
        schemes = try {
            appJson.decodeFromString<ThemeJson>(
                Res.readBytes(
                    when (appThemeConfig.value) {
                        AppThemeConfig.Green -> "files/material-theme-green.json"
                        AppThemeConfig.Blue -> "files/material-theme-blue.json"
                        AppThemeConfig.Red -> "files/material-theme-red.json"
                        AppThemeConfig.Yellow -> "files/material-theme-yellow.json"
                    }
                ).decodeToString()
            ).schemes
        } catch (e: Exception) {
            Schemes()
        }
        val innerScheme = if (darkTheme) {
            schemes.dark
        } else {
            schemes.light
        }
        colors.value = lightColorScheme(
            primary = innerScheme?.primary.orEmpty().toColor(),
            onPrimary = innerScheme?.onPrimary.orEmpty().toColor(),
            primaryContainer = innerScheme?.primaryContainer.orEmpty().toColor(),
            onPrimaryContainer = innerScheme?.onPrimaryContainer.orEmpty().toColor(),
            inversePrimary = innerScheme?.inversePrimary.orEmpty().toColor(),
            secondary = innerScheme?.secondary.orEmpty().toColor(),
            onSecondary = innerScheme?.onSecondary.orEmpty().toColor(),
            secondaryContainer = innerScheme?.secondaryContainer.orEmpty().toColor(),
            onSecondaryContainer = innerScheme?.onSecondaryContainer.orEmpty().toColor(),
            tertiary = innerScheme?.tertiary.orEmpty().toColor(),
            onTertiary = innerScheme?.onTertiary.orEmpty().toColor(),
            tertiaryContainer = innerScheme?.tertiaryContainer.orEmpty().toColor(),
            onTertiaryContainer = innerScheme?.onTertiaryContainer.orEmpty().toColor(),
            background = innerScheme?.background.orEmpty().toColor(),
            onBackground = innerScheme?.onBackground.orEmpty().toColor(),
            surface = innerScheme?.surface.orEmpty().toColor(),
            onSurface = innerScheme?.onSurface.orEmpty().toColor(),
            surfaceVariant = innerScheme?.surfaceVariant.orEmpty().toColor(),
            onSurfaceVariant = innerScheme?.onSurfaceVariant.orEmpty().toColor(),
            surfaceTint = innerScheme?.surfaceTint.orEmpty().toColor(),
            inverseSurface = innerScheme?.inverseSurface.orEmpty().toColor(),
            inverseOnSurface = innerScheme?.inverseOnSurface.orEmpty().toColor(),
            error = innerScheme?.error.orEmpty().toColor(),
            onError = innerScheme?.onError.orEmpty().toColor(),
            errorContainer = innerScheme?.errorContainer.orEmpty().toColor(),
            onErrorContainer = innerScheme?.onErrorContainer.orEmpty().toColor(),
            outline = innerScheme?.outline.orEmpty().toColor(),
            outlineVariant = innerScheme?.outlineVariant.orEmpty().toColor(),
            scrim = innerScheme?.scrim.orEmpty().toColor(),
            surfaceBright = innerScheme?.surfaceBright.orEmpty().toColor(),
            surfaceContainer = innerScheme?.surfaceContainer.orEmpty().toColor(),
            surfaceContainerHigh = innerScheme?.surfaceContainerHigh.orEmpty().toColor(),
            surfaceContainerHighest = innerScheme?.surfaceContainerHighest.orEmpty().toColor(),
            surfaceContainerLow = innerScheme?.surfaceContainerLow.orEmpty().toColor(),
            surfaceContainerLowest = innerScheme?.surfaceContainerLowest.orEmpty().toColor(),
            surfaceDim = innerScheme?.surfaceDim.orEmpty().toColor(),
        )
    }
    val shapes = Shapes(
        extraSmall = RoundedCornerShape(2.dp),
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp),
        extraLarge = RoundedCornerShape(16.dp)
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
        LocalAppDimens provides dimens, LocalAppDialogPadding provides dialogPadding, LocalContentPadding provides contentPadding
    ) {
        MaterialTheme(colorScheme = colors.value, typography = typography.value, shapes = shapes, content = content)
    }
}
