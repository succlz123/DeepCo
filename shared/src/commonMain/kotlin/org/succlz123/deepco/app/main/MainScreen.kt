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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.main.MainViewModel.Companion.MAIN_TITLE
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
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            MainTab(
                Modifier.fillMaxHeight().background(MaterialTheme.colorScheme.surfaceDim),
                { homeVm.selectItem.value = it },
                { selectItem.value })
            Column(Modifier.background(MaterialTheme.colorScheme.surface).fillMaxHeight().weight(1f)) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(0.dp, 18.dp, 0.dp, 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                }
                Box(Modifier.fillMaxHeight().weight(1f)) {
                    selectItem.value.content.invoke()
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            MainTab(
                Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceDim),
                { homeVm.selectItem.value = it },
                { selectItem.value })
            Box(Modifier.background(MaterialTheme.colorScheme.surface).fillMaxWidth().weight(1f)) {
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
            }, shape = MaterialTheme.shapes.medium,   elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = if (getLeftSelect() == item) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Transparent
            })) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp, 6.dp).size(42.dp)
            ) {
                Icon(
                    item.icon,
                    modifier = Modifier.size(20.dp),
                    contentDescription = item.name.invoke(),
                    tint = if (getLeftSelect() == item) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    item.name.invoke(), style = MaterialTheme.typography.labelMedium, color = if (getLeftSelect() == item) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
