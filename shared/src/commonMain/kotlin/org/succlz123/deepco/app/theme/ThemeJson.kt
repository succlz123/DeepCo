package org.succlz123.deepco.app.theme

import kotlinx.serialization.Serializable

@Serializable
data class ThemeJson(
    val coreColors: CoreColors,
    val description: String,
    val extendedColors: List<ExtendedColor> = emptyList(),
    val palettes: Palettes? = null,
    val schemes: Schemes = Schemes(),
    val seed: String = ""
)

@Serializable
data class CoreColors(
    val neutral: String? = null,
    val neutralVariant: String? = null,
    val primary: String? = null,
    val secondary: String? = null,
    val tertiary: String? = null
)

@Serializable
data class ExtendedColor(
    val color: String? = null,
    val description: String? = null,
    val harmonized: Boolean? = null,
    val name: String? = null
)

@Serializable
data class Palettes(
    val neutral: Percentage? = null,
    val `neutral-variant`: Percentage? = null,
    val primary: Percentage? = null,
    val secondary: Percentage? = null,
    val tertiary: Percentage? = null
)

@Serializable
data class Schemes(
    val dark: ColorTheme? = null,
    val `dark-high-contrast`: ColorTheme? = null,
    val `dark-medium-contrast`: ColorTheme? = null,
    val light: ColorTheme? = null,
    val `light-high-contrast`: ColorTheme? = null,
    val `light-medium-contrast`: ColorTheme? = null
)

@Serializable
data class Percentage(
    val `0`: String? = null,
    val `5`: String? = null,
    val `10`: String? = null,
    val `15`: String? = null,
    val `20`: String? = null,
    val `25`: String? = null,
    val `30`: String? = null,
    val `35`: String? = null,
    val `40`: String? = null,
    val `50`: String? = null,
    val `60`: String? = null,
    val `70`: String? = null,
    val `80`: String? = null,
    val `90`: String? = null,
    val `95`: String? = null,
    val `98`: String? = null,
    val `99`: String? = null,
    val `100`: String? = null,
)

@Serializable
data class ColorTheme(
    val background: String? = null,
    val error: String? = null,
    val errorContainer: String? = null,
    val inverseOnSurface: String? = null,
    val inversePrimary: String? = null,
    val inverseSurface: String? = null,
    val onBackground: String? = null,
    val onError: String? = null,
    val onErrorContainer: String? = null,
    val onPrimary: String? = null,
    val onPrimaryContainer: String? = null,
    val onPrimaryFixed: String? = null,
    val onPrimaryFixedVariant: String? = null,
    val onSecondary: String? = null,
    val onSecondaryContainer: String? = null,
    val onSecondaryFixed: String? = null,
    val onSecondaryFixedVariant: String? = null,
    val onSurface: String? = null,
    val onSurfaceVariant: String? = null,
    val onTertiary: String? = null,
    val onTertiaryContainer: String? = null,
    val onTertiaryFixed: String? = null,
    val onTertiaryFixedVariant: String? = null,
    val outline: String? = null,
    val outlineVariant: String? = null,
    val primary: String? = null,
    val primaryContainer: String? = null,
    val primaryFixed: String? = null,
    val primaryFixedDim: String? = null,
    val scrim: String? = null,
    val secondary: String? = null,
    val secondaryContainer: String? = null,
    val secondaryFixed: String? = null,
    val secondaryFixedDim: String? = null,
    val shadow: String? = null,
    val surface: String? = null,
    val surfaceBright: String? = null,
    val surfaceContainer: String? = null,
    val surfaceContainerHigh: String? = null,
    val surfaceContainerHighest: String? = null,
    val surfaceContainerLow: String? = null,
    val surfaceContainerLowest: String? = null,
    val surfaceDim: String? = null,
    val surfaceTint: String? = null,
    val surfaceVariant: String? = null,
    val tertiary: String? = null,
    val tertiaryContainer: String? = null,
    val tertiaryFixed: String? = null,
    val tertiaryFixedDim: String? = null
)
