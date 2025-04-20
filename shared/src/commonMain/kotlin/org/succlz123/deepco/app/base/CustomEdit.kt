package org.succlz123.deepco.app.base

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomEdit(
    text: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    paddingValues: PaddingValues = PaddingValues(
        start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp
    ),
    hint: String = "",
    startIcon: ImageVector? = null,
    tintIconColor: Color = Color.Black,
    iconSpacing: Dp = 6.dp,

    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    scrollHeight: Dp = 0.dp,

    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.primary)
) {
    val hasFocus = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.then(
            if (scrollHeight > 0.dp) {
                Modifier.height(scrollHeight)
            } else {
                Modifier
            }
        )
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainerLow, shape = MaterialTheme.shapes.large).padding(paddingValues).onFocusChanged { hasFocus.value = it.isFocused }.then(
                if (scrollHeight > 0.dp) {
                    Modifier.verticalScroll(rememberScrollState())
                } else {
                    Modifier
                }
            ),
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            cursorBrush = cursorBrush,
            decorationBox = @Composable { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Image(
                            imageVector = startIcon,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(color = tintIconColor)
                        )
                        Spacer(modifier = Modifier.width(iconSpacing))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (text.isEmpty()) {
                            Text(text = hint, color = Color.LightGray, style = textStyle)
                        }
                        innerTextField()
                    }
                    if (hasFocus.value && text.isNotEmpty()) {
                        Image(
                            imageVector = Icons.Sharp.Clear,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = tintIconColor),
                            modifier = Modifier.size(18.dp).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                                onValueChange.invoke("")
                            })
                    }
                }
            }
        )
    }
}
