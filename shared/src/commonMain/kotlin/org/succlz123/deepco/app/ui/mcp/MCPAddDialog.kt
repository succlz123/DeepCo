package org.succlz123.deepco.app.ui.mcp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.deepco.app.base.AppConfirmButton
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleColumnScroll
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun MCPAddDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainMcpViewModel()
    }
    val name = remember { mutableStateOf("") }
    val command = remember { mutableStateOf("") }
    val args = remember { mutableStateOf("") }
    BaseDialogCardWithTitleColumnScroll(strings().mcpConfig, bottomFixedContent = {
        Box(modifier = Modifier.fillMaxWidth()) {
            AppConfirmButton(Modifier.align(Alignment.BottomEnd), onClick = {
                vm.add(name.value, command.value, args.value.split(" "))
                screenNavigator.pop()
            })
        }
    }) {
        Text(
            text = strings().name, modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            name.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            hint = "123",
            onValueChange = {
                name.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = strings().command, modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            command.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            hint = "npx",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                command.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = strings().args, modifier = Modifier, color = ColorResource.black, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomEdit(
            args.value,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            hint = "-y @modelcontextprotocol/server-filesystem ~/Downloads",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                args.value = it
            }, modifier = Modifier.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}