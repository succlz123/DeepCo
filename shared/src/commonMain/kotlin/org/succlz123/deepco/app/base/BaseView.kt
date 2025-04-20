package org.succlz123.deepco.app.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_confirm
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator


@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp), color = MaterialTheme.colorScheme.primary, strokeWidth = 6.dp
        )
    }
}

@Composable
fun AppRefreshButton(modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Box(modifier = Modifier.noRippleClick(onClick).padding(12.dp)) {
            Icon(
                Icons.Sharp.Refresh, modifier = Modifier.size(32.dp).padding(2.dp), contentDescription = "Refresh", tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AppAddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Box(modifier = Modifier.noRippleClick(onClick).padding(12.dp)) {
            Icon(
                Icons.Default.Add, modifier = Modifier.size(32.dp).padding(2.dp), contentDescription = "Refresh",
                tint = MaterialTheme.colorScheme.primary
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
    Spacer(modifier = modifier.height(1.dp).background(MaterialTheme.colorScheme.surfaceContainer))
}

@Composable
fun AppVerticalDivider(modifier: Modifier = Modifier.fillMaxHeight()) {
    Spacer(modifier = modifier.width(1.dp).background(MaterialTheme.colorScheme.surfaceContainer))
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
        Text(text, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyMedium)
    })
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier, contentPaddingValues: PaddingValues, onClick: () -> Unit, content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium).noRippleClick {
            onClick.invoke()
        }.padding(contentPaddingValues)
    ) {
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
fun AppImgButton(modifier: Modifier = Modifier, size: Dp = 14.dp, tint: Color = MaterialTheme.colorScheme.onPrimary, drawable: DrawableResource, text: String, onClick: () -> Unit) {
    AppButton(
        modifier, contentPaddingValues = PaddingValues(
            start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
        ), onClick = onClick, content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(size),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(tint),
                    painter = painterResource(resource = drawable),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyMedium)
            }
        })
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
                Text(text, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyMedium)
            }
        })
}

@Composable
fun AppConfirmButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    AppImgButton(modifier, imageContent = {
        Image(
            modifier = Modifier.size(14.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            painter = painterResource(resource = Res.drawable.ic_confirm),
        )
    }, strings().confirm, onClick)
}

@Composable
fun AsteriskText(text: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, modifier = modifier, style = MaterialTheme.typography.titleMedium)
        Text(text = "*", modifier = Modifier, style = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.error))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSliderBar(modifier: Modifier, min: Float, max: Float, progress: Float, showText: Boolean = false, change: (Float) -> Unit) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        if (showText) {
            Text(min.toString(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(6.dp))
        }
        Slider(
            modifier = Modifier.weight(1f), value = progress, valueRange = min..max, onValueChange = {
                change(it)
            }, thumb = {
                SliderDefaults.Thumb(
                    modifier = Modifier.size(16.dp),
                    interactionSource = remember { MutableInteractionSource() },
                    colors = SliderDefaults.colors(),
                    enabled = true
                )
            }, track = {
                SliderDefaults.Track(
                    modifier = Modifier.fillMaxWidth().height(10.dp),
                    colors = colors(), enabled = true, sliderState = it
                )
            }, colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant,
                activeTrackColor = MaterialTheme.colorScheme.primary,
            )
        )
        if (showText) {
            Spacer(Modifier.width(6.dp))
            Text(max.toString(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}