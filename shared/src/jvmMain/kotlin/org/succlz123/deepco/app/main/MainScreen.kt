package org.succlz123.deepco.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import org.succlz123.deepco.app.ui.chat.MainChatTab
import org.succlz123.deepco.app.ui.llm.MainLLMTab
import org.succlz123.deepco.app.ui.mcp.MainMcpTab
import org.succlz123.deepco.app.ui.setting.MainSettingTab
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.prompt.MainPromptTab
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.screen.viewmodel.viewModel

@Composable
fun MainScreen() {
    val homeVm = viewModel(MainViewModel::class) {
        MainViewModel()
    }
    val leftSelectItem = homeVm.leftSelectItem.collectAsState()
    Row(modifier = Modifier.fillMaxSize().background(Color.White), verticalAlignment = Alignment.CenterVertically) {
        MainLeft(
            Modifier.fillMaxHeight().background(ColorResource.background),
            { homeVm.leftSelectItem.value = it },
            { leftSelectItem.value })
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().padding(0.dp, 18.dp, 0.dp, 8.dp),
                contentAlignment = Alignment.Center
            ) {
            }
            MainRight(
                Modifier.background(Color.White).fillMaxHeight().weight(1f)
            ) { leftSelectItem.value }
        }
    }
}

@Composable
fun MainLeft(
    modifier: Modifier,
    changeLeftSelect: (Int) -> Unit,
    getLeftSelect: () -> Int
) {
    val scrollState = rememberLazyListState()
    LazyColumn(modifier = modifier.padding(12.dp), state = scrollState, verticalArrangement = Arrangement.Center) {
        this.mainLeft(changeLeftSelect, getLeftSelect)
    }
}

fun LazyListScope.mainLeft(changeLeftSelect: (Int) -> Unit, getLeftSelect: () -> Int) {
    itemsIndexed(MainViewModel.MAIN_TITLE) { index, item ->
        Card(
            modifier = Modifier.noRippleClickable {
                changeLeftSelect(index)
            }, shape = RoundedCornerShape(8.dp), elevation = 0.dp, backgroundColor = if (getLeftSelect() == index) {
                ColorResource.theme
            } else {
                Color.Transparent
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp, 8.dp).size(42.dp)
            ) {
                Icon(
                    MainViewModel.MAIN_ICON[index],
                    modifier = Modifier.size(20.dp),
                    contentDescription = item,
                    tint = if (getLeftSelect() == index) {
                        Color.White
                    } else {
                        Color.LightGray
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    item, style = MaterialTheme.typography.body1, color = if (getLeftSelect() == index) {
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

@Composable
fun MainRight(modifier: Modifier, leftSelectItem: () -> Int) {
    when (leftSelectItem()) {
        0 -> {
            MainChatTab(modifier = modifier)
        }

        1 -> {
            MainLLMTab(modifier = modifier)
        }

        2 -> {
            MainPromptTab(modifier = modifier)
        }

        3 -> {
            MainMcpTab(modifier = modifier)
        }

        4 -> {
            MainSettingTab(modifier = modifier)
        }

        else -> {
            Box(modifier = modifier)
        }
    }
}

