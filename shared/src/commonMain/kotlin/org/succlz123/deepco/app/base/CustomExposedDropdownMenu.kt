package org.succlz123.deepco.app.base

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

open class DropdownMenuDes(val showName: String, val tag: Any)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomExposedDropdownMenu(
    options: List<out DropdownMenuDes>,
    labelStr: String,
    defaultName: String? = null,
    onSelect: (DropdownMenuDes) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOptionText by remember { mutableStateOf("") }
    LaunchedEffect(defaultName) {
        val found = options.find { it.showName == defaultName }
        if (found != null) {
            selectedOptionText = found.showName
            onSelect(found)
        }
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }, modifier = Modifier
    ) {
        TextField(
            value = selectedOptionText,
            modifier = Modifier.menuAnchor(),
            onValueChange = { v: String -> selectedOptionText = v },
            label = {
                Text(labelStr, style = MaterialTheme.typography.bodyMedium)
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                disabledLabelColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            readOnly = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 620.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option.showName) }, onClick = {
                    selectedOptionText = option.showName
                    expanded = false
                    onSelect(option)
                })
            }
        }
    }
}