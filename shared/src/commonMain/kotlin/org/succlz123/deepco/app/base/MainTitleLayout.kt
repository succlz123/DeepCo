package org.succlz123.deepco.app.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainTitleLayout(
    modifier: Modifier = Modifier,
    text: String,
    subText: String = "",
    topRightContent: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp, 10.dp, 16.dp, 10.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(1.dp).width(12.dp))
            Text(text = subText, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(1.dp).weight(1f))
            topRightContent?.invoke()
        }
        AppHorizontalDivider()
        content()
    }
}