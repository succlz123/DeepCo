package org.succlz123.deepco.app.base

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.succlz123.deepco.app.theme.ColorResource

open class DropdownMenuDes(val showName: String, val tag: Any)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomExposedDropdownMenu(
    options: List<out DropdownMenuDes>,
    labelStr: String,
    onSelect: (DropdownMenuDes) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOptionText by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }, modifier = Modifier
    ) {
        TextField(
//            modifier = Modifier.align(Alignment.Center),
            value = selectedOptionText,
            onValueChange = { v: String -> selectedOptionText = v },
            label = {
                Text(labelStr, color = ColorResource.secondaryText, fontSize = 14.sp)
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = ColorResource.theme,
                textColor = ColorResource.theme,
                backgroundColor = ColorResource.background,
                focusedLabelColor = ColorResource.black,
                disabledLabelColor = ColorResource.divider
            ),
            modifier = Modifier,
//                .background(ColorResource.green)
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 620.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(content = { Text(option.showName) }, onClick = {
                    selectedOptionText = option.showName
                    expanded = false
                    onSelect(option)
                })
            }
        }
    }
}