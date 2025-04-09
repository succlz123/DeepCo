package org.succlz123.deepco.app.base

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Clear
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
import javax.swing.Spring.height

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomEdit(
    text: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    hint: String = "请输入",
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
    cursorBrush: Brush = SolidColor(MaterialTheme.colors.primary)
) {
    // 焦点, 用于控制是否显示 右侧叉号
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
            modifier = modifier.onFocusChanged { hasFocus.value = it.isFocused }.then(
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
                        // 当空字符时, 显示hint
                        if (text.isEmpty()) {
                            Text(text = hint, color = Color.LightGray, style = textStyle)
                        }
                        // 原本输入框的内容
                        innerTextField()
                    }

                    // 存在焦点 且 有输入内容时. 显示叉号
                    if (hasFocus.value && text.isNotEmpty()) {
                        Image(
                            imageVector = Icons.Sharp.Clear,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = tintIconColor),
                            modifier = Modifier.size(18.dp).clickable {
                                onValueChange.invoke("")
                            })
                    }
                }
            }
        )
    }
}
