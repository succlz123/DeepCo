package org.succlz123.deepco.app.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainRightTitleLayout(
    modifier: Modifier = Modifier,
    text: String,
    subText: String = "",
    topRightContent: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.background(Color.White)) {
        Row(
            modifier = Modifier.padding(18.dp, 12.dp, 18.dp, 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(1.dp).width(12.dp))
            Text(text = subText, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(1.dp).weight(1f))
            topRightContent?.invoke()
        }
        AppHorizontalDivider()
        content()
    }
}