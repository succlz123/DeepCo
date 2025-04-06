package org.succlz123.deepco.app.ui.resize

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.deepco.app.theme.ColorResource

@Composable
fun ResizablePanelTabView(text: String) = Surface {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            color = ColorResource.primaryText,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
    }
}