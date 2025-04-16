package org.succlz123.deepco.app.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.theme.LocalAppDialogPadding
import org.succlz123.deepco.app.theme.LocalContentPadding
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.modifier.shadow
import org.succlz123.lib.screen.LocalScreenNavigator

@Composable
fun BaseDialogCard(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(ColorResource.black.copy(0.1f)).padding(LocalAppDialogPadding.current).noRippleClick {}, contentAlignment = Alignment.Center) {
        Card(modifier = Modifier.fillMaxSize().align(Alignment.Center).shadow(), elevation = 0.dp, backgroundColor = Color.White, content = content)
    }
}

@Composable
fun BaseDialogCardWithTitleColumnScroll(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val screenNavigator = LocalScreenNavigator.current
    BaseDialogCard {
        Column(modifier = Modifier.padding(LocalContentPadding.current)) {
            Row {
                Text(
                    modifier = Modifier,
                    text = title,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.size(14.dp).noRippleClick {
                        screenNavigator.pop()
                    },
                    contentDescription = null,
                    painter = painterResource(resource = Res.drawable.ic_close),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppHorizontalDivider()
            Column(modifier = Modifier.verticalScroll(state = rememberScrollState()).padding(vertical = 16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun BaseDialogCardWithTitleNoneScroll(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val screenNavigator = LocalScreenNavigator.current
    BaseDialogCard {
        Column(modifier = Modifier.padding(LocalContentPadding.current)) {
            Row {
                Text(
                    modifier = Modifier,
                    text = title,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.size(14.dp).noRippleClick {
                        screenNavigator.pop()
                    },
                    contentDescription = null,
                    painter = painterResource(resource = Res.drawable.ic_close),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}