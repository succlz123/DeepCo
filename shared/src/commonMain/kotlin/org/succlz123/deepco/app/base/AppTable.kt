package org.succlz123.deepco.app.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.theme.ColorResource

interface TabIndexGet {

    fun getFieldByIndex(index: Int): String?
}

class TableTitleItem(val title: String, val width: Int, val weight: Float)


@Composable
fun AppTable(modifier: Modifier, titleList: List<TableTitleItem>, content: List<TabIndexGet>, indexInterceptor: @Composable ((index: Int, item: TabIndexGet) -> Boolean)? = null) {
    val titleSizeMap = remember { mutableStateMapOf<Int, Int>() }
    LazyColumn(modifier = modifier) {
        item {
            Row(
                modifier = Modifier.background(ColorResource.background, shape = RoundedCornerShape(4.dp))
            ) {
                titleList.forEachIndexed { index, titleItem ->
                    Text(
                        text = titleItem.title, modifier = Modifier.then(
                            if (titleItem.width != 0) {
                                Modifier.width(titleItem.width.dp)
                            } else {
                                Modifier.weight(1f)
                            }
                        ).onSizeChanged {
                            titleSizeMap[index] = it.width
                        }.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.h5
                    )
                }
            }
        }
        itemsIndexed(content, key = { index, item ->
            item.toString()
        }) { index, item ->
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                titleList.forEachIndexed { index, titleItem ->
                    if (indexInterceptor != null && indexInterceptor.invoke(index, item)) {
                    } else {
                        Text(
                            text = item.getFieldByIndex(index).orEmpty(),
                            modifier = Modifier.width(((titleSizeMap[index] ?: 1) / LocalDensity.current.density).dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            AppHorizontalDivider()
        }
    }
}

