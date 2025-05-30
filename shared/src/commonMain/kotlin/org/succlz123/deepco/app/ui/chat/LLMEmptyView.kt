package org.succlz123.deepco.app.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.i18n.strings

@Composable
fun LLMEmptyView() = Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        Icon(
            Icons.Default.AddCircle,
            contentDescription = null,
            tint = LocalContentColor.current.copy(alpha = 0.60f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            strings().errorLLMProviderIsEmpty,
            color = LocalContentColor.current.copy(alpha = 0.60f),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        )
    }
}