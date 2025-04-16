package org.succlz123.deepco.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.main.MainViewModel.Companion.MAIN_TITLE
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.viewmodel.viewModel
import org.succlz123.lib.screen.window.rememberIsWindowExpanded

@Composable
fun MainScreen() {
    val homeVm = viewModel(MainViewModel::class) {
        MainViewModel()
    }
    val isExpandedScreen = rememberIsWindowExpanded()
    val selectItem = homeVm.selectItem.collectAsState()
    if (isExpandedScreen) {
        Row(modifier = Modifier.fillMaxSize().background(ColorResource.white), verticalAlignment = Alignment.CenterVertically) {
            MainTab(
                Modifier.fillMaxHeight().background(ColorResource.background),
                { homeVm.selectItem.value = it },
                { selectItem.value })
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(0.dp, 18.dp, 0.dp, 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                }
                Box(Modifier.background(ColorResource.white).fillMaxHeight().weight(1f)) {
                    selectItem.value.content.invoke()
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().background(ColorResource.white)) {
            MainTab(
                Modifier.fillMaxWidth().background(ColorResource.background),
                { homeVm.selectItem.value = it },
                { selectItem.value })
            Box(Modifier.background(ColorResource.white).fillMaxWidth().weight(1f)) {
                selectItem.value.content.invoke()
            }
        }
    }
}

@Composable
private fun MainTab(
    modifier: Modifier,
    changeSelect: (MainSelectItem) -> Unit,
    getSelect: () -> MainSelectItem
) {
    val isExpandedScreen = rememberIsWindowExpanded()
    val scrollState = rememberLazyListState()
    if (isExpandedScreen) {
        LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 12.dp), state = scrollState, verticalArrangement = Arrangement.Center) {
            this.mainTab(changeSelect, getSelect)
        }
    } else {
        LazyRow(modifier = modifier, contentPadding = PaddingValues(horizontal = 12.dp), state = scrollState, horizontalArrangement = Arrangement.Center) {
            this.mainTab(changeSelect, getSelect)
        }
    }
}

private fun LazyListScope.mainTab(changeSelect: (MainSelectItem) -> Unit, getLeftSelect: () -> MainSelectItem) {
    itemsIndexed(MAIN_TITLE) { index, item ->
        Card(
            modifier = Modifier.noRippleClick {
                changeSelect(item)
            }, shape = RoundedCornerShape(8.dp), elevation = 0.dp, backgroundColor = if (getLeftSelect() == item) {
                ColorResource.theme
            } else {
                Color.Transparent
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp, 6.dp).size(42.dp)
            ) {
                Icon(
                    item.icon,
                    modifier = Modifier.size(20.dp),
                    contentDescription = item.name,
                    tint = if (getLeftSelect() == item) {
                        Color.White
                    } else {
                        Color.LightGray
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    item.name, style = MaterialTheme.typography.body2, color = if (getLeftSelect() == item) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
