
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainSearchTab(modifier: Modifier = Modifier, isExpandedScreen: Boolean) {
//    val searchVm = viewModel {
//        HomeSearchViewModel()
//    }
//    val inputText = searchVm.searchText.collectAsState()
//
//    val focusRequester = remember { FocusRequester() }
//
//    MainRightTitleLayout(modifier, text = "搜索", topRightContent = {
//        Row {
//            BasicTextField(
//                modifier = Modifier.width(280.dp).background(ColorResource.background).padding(18.dp, 12.dp),
//                textStyle = TextStyle(fontSize = 16.sp),
//                maxLines = 1,
//                value = inputText.value,
//                onValueChange = { searchVm.searchText.value = it },
//            )
//            Spacer(modifier = Modifier.width(18.dp))
//            Card(backgroundColor = Color.White, elevation = 6.dp) {
//                Box(modifier = Modifier.padding(12.dp).noRippleClickable {
//                    searchVm.search()
//                }) {
//                    Icon(
//                        Icons.Sharp.Search,
//                        modifier = Modifier.size(18.dp),
//                        contentDescription = "Search",
//                        tint = ColorResource.theme
//                    )
//                }
//            }
//        }
//    }) {
//        MainHomeContentItem(
//            result = searchVm.search.collectAsState().value,
//
//            thisRequester = focusRequester,
//            otherRequester = null,
//
//            isExpandedScreen = isExpandedScreen,
//            onRefresh = {
//                searchVm.search()
//            }) { searchVm.loadMore() }
//    }
}
