package org.succlz123.deepco.app.ui.chat

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_copy
import deep_co.shared.generated.resources.ic_down
import deep_co.shared.generated.resources.ic_elapsed
import deep_co.shared.generated.resources.ic_model
import deep_co.shared.generated.resources.ic_remove
import deep_co.shared.generated.resources.ic_show_more
import deep_co.shared.generated.resources.ic_token_down
import deep_co.shared.generated.resources.ic_token_total
import deep_co.shared.generated.resources.ic_token_up
import deep_co.shared.generated.resources.logo_deepseek
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.base.shadow
import org.succlz123.deepco.app.llm.ChatMessage
import org.succlz123.deepco.app.llm.role.RoleDefine
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.theme.LocalTheme
import org.succlz123.deepco.app.theme.Theme
import org.succlz123.deepco.app.ui.chat.ChatViewModel.Companion.DEFAULT_STREAM_MODEL
import org.succlz123.deepco.app.ui.chat.ChatViewModel.Companion.STREAM_LIST
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel
import org.succlz123.deepco.app.ui.mcp.MainMcpViewModel
import org.succlz123.deepco.app.ui.resize.PanelState
import org.succlz123.deepco.app.ui.resize.ResizablePanel
import org.succlz123.deepco.app.ui.resize.ResizablePanelTabView
import org.succlz123.deepco.app.util.VerticalSplittable
import org.succlz123.lib.click.clickUrl
import org.succlz123.lib.click.noClickableAndCopyStr
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.common.openURLByBrowser
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.result.ScreenResult
import org.succlz123.lib.screen.viewmodel.globalViewModel


@Composable
fun MainChatTab(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(0.dp, 0.dp, 0.dp, 0.dp).fillMaxSize()) {
        DisableSelection {
            val theme = if (isSystemInDarkTheme()) Theme.dark else Theme.light
            CompositionLocalProvider(LocalTheme provides theme) {
                MaterialTheme(colors = theme.materialColors) {
                    ChatView()
                }
            }
        }
    }
}


@Composable
fun ChatView() {
    val panelState = remember { PanelState() }

    val animatedSize = if (panelState.splitter.isResizing) {
        if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize
    } else {
        animateDpAsState(
            if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize, SpringSpec(stiffness = StiffnessLow)
        ).value
    }

    val llmViewModel = globalViewModel {
        MainLLMViewModel()
    }
    val mcpViewModel = globalViewModel {
        MainMcpViewModel()
    }
    val chatViewModel = remember {
        ChatViewModel()
    }
    DisposableEffect(Unit) {
        onDispose {
            chatViewModel.clear()
        }
    }
    val screenNavigator = LocalScreenNavigator.current
    Box(
        Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        VerticalSplittable(Modifier.fillMaxSize(), panelState.splitter, onResize = {
            panelState.expandedSize = (panelState.expandedSize + it).coerceAtLeast(panelState.expandedSizeMin)
        }) {
            ResizablePanel(Modifier.width(animatedSize).fillMaxHeight(), panelState) {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    ResizablePanelTabView("History")
                    Spacer(Modifier.height(12.dp))
                    AppHorizontalDivider()
                    if (panelState.isExpanded) {
                        Spacer(Modifier.height(6.dp))
                        chatViewModel.history.collectAsState().value.entries.reversed().forEach {
                            val curSelect = chatViewModel.selectedHistory.collectAsState().value?.id
                            Text(
                                it.value.list.firstOrNull()?.createdTimeFormatted().orEmpty(),
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                maxLines = 1,
                                color = ColorResource.secondaryText,
                                style = MaterialTheme.typography.overline
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    it.value.list.lastOrNull()?.content?.value.orEmpty(),
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f).noRippleClickable {
                                        chatViewModel.updateHistory()
                                        chatViewModel.selectedHistory.value = it.value
                                        chatViewModel.preChatMessage.value = it.value
                                    }.background(if (curSelect == it.key) ColorResource.theme else ColorResource.transparent, shape = RoundedCornerShape(4.dp)).padding(vertical = 4.dp, horizontal = 6.dp),
                                    maxLines = 1,
                                    color = if (curSelect == it.key) ColorResource.white else ColorResource.primaryText,
                                    style = MaterialTheme.typography.body2
                                )
                                Spacer(Modifier.width(6.dp))
                                Image(
                                    modifier = Modifier.size(14.dp).noRippleClickable {
                                        chatViewModel.removeHistory(it.key)
                                    },
                                    contentDescription = null,
                                    painter = painterResource(resource = Res.drawable.ic_remove),
                                )
                                Spacer(Modifier.width(6.dp))
                            }
                        }
                    }
                }
            }
            Box() {
                val selectedLLMConfig = llmViewModel.selectedLLMName.collectAsState()
                val selectedLLmConfigValue = selectedLLMConfig.value
                if (selectedLLmConfigValue != null) {
                    val selectedLLMModel = llmViewModel.selectedLLMModel.collectAsState()
                    Column(Modifier.fillMaxSize()) {
//                        EditorTabsView(model.editors)
//                        StatusBars(scalableState, model.editors.active?.file?.absolutePath.orEmpty())
                        Row(Modifier.height(48.dp).fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                            val providerExpanded = remember {
                                mutableStateOf(false)
                            }
                            Row(
                                modifier = Modifier.background(ColorResource.theme.copy(0.1f), shape = RoundedCornerShape(4.dp)).padding(horizontal = 12.dp, vertical = 6.dp).noRippleClickable {
                                    providerExpanded.value = true
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                DropdownMenu(
                                    expanded = providerExpanded.value,
                                    modifier = Modifier,
                                    onDismissRequest = {},
                                    content = {
                                        llmViewModel.llmConfigs.value.orEmpty().forEach {
                                            DropdownMenuItem(contentPadding = PaddingValues(12.dp, 4.dp), onClick = {
                                                providerExpanded.value = !providerExpanded.value
                                                llmViewModel.saveDefaultLLM(it)
                                            }, content = {
                                                Text(text = it.provider + " " + it.name, color = ColorResource.secondaryText, style = MaterialTheme.typography.body2)
                                            })
                                        }
                                    },
                                )
                                Image(
                                    painter = painterResource(resource = Res.drawable.logo_deepseek), contentDescription = "logo", modifier = Modifier.width(60.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(selectedLLmConfigValue.name, color = ColorResource.theme, style = MaterialTheme.typography.body2)
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(resource = Res.drawable.ic_down),
                                    contentDescription = "down",
                                    colorFilter = ColorFilter.tint(ColorResource.theme),
                                    modifier = Modifier.width(10.dp).noRippleClickable {})
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            val modeExpanded = remember {
                                mutableStateOf(false)
                            }
                            Row(
                                modifier = Modifier.background(ColorResource.theme.copy(0.1f), shape = RoundedCornerShape(4.dp)).padding(horizontal = 12.dp, vertical = 6.dp).noRippleClickable {
                                    modeExpanded.value = true
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                DropdownMenu(
                                    expanded = modeExpanded.value,
                                    modifier = Modifier,
                                    onDismissRequest = {},
                                    content = {
                                        selectedLLmConfigValue.modes.forEach {
                                            DropdownMenuItem(contentPadding = PaddingValues(12.dp, 4.dp), onClick = {
                                                modeExpanded.value = !modeExpanded.value
                                                llmViewModel.saveDefaultLLMModel(it)
                                            }, content = {
                                                Text(text = it, color = ColorResource.secondaryText, style = MaterialTheme.typography.body2)
                                            })
                                        }
                                    },
                                )
                                Image(
                                    painter = painterResource(resource = Res.drawable.ic_model), contentDescription = "model", colorFilter = ColorFilter.tint(ColorResource.theme), modifier = Modifier.width(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(selectedLLMModel.value.orEmpty(), color = ColorResource.theme, style = MaterialTheme.typography.body2)
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(resource = Res.drawable.ic_down),
                                    contentDescription = "down",
                                    colorFilter = ColorFilter.tint(ColorResource.theme),
                                    modifier = Modifier.width(10.dp).noRippleClickable {})
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            AppButton(
                                modifier = Modifier, contentPaddingValues = PaddingValues(
                                    start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                                ), {
                                    chatViewModel.clear()
                                }, {
                                    Text("New Chat", color = ColorResource.white, style = MaterialTheme.typography.body1)
                                })
                        }
                        AppHorizontalDivider()
                        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("ROLE", color = ColorResource.primaryText, style = MaterialTheme.typography.h5)
                            Spacer(modifier = Modifier.width(12.dp))
                            val selectedRole = chatViewModel.selectedRole.collectAsState().value
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LazyRow(modifier = Modifier.weight(1f)) {
                                    itemsIndexed(RoleDefine.roles) { index, role ->
                                        Box(
                                            modifier = Modifier.background(
                                                if (selectedRole.roleName == role.roleName) {
                                                    ColorResource.theme
                                                } else {
                                                    ColorResource.white
                                                }, shape = RoundedCornerShape(4.dp)
                                            ).border(
                                                BorderStroke(
                                                    1.dp, if (selectedRole.roleName == role.roleName) {
                                                        ColorResource.white
                                                    } else {
                                                        ColorResource.theme
                                                    }
                                                ), shape = RoundedCornerShape(4.dp)
                                            ).padding(horizontal = 10.dp, vertical = 4.dp).noRippleClickable {
                                                chatViewModel.selectedRole.value = role
                                            }) {
                                            Text(
                                                role.roleName, color = if (selectedRole.roleName == role.roleName) {
                                                    ColorResource.white
                                                } else {
                                                    ColorResource.theme
                                                }, style = MaterialTheme.typography.body2
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                    }
                                }
                                Image(
                                    modifier = Modifier.size(12.dp).noRippleClickable {},
                                    contentDescription = null,
                                    painter = painterResource(resource = Res.drawable.ic_show_more),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        AppHorizontalDivider()
                        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("TOOL", color = ColorResource.primaryText, style = MaterialTheme.typography.h5)
                            Spacer(modifier = Modifier.width(12.dp))
                            val mcpList = mcpViewModel.mcpList.collectAsState().value
                            val selectedMCPList = chatViewModel.selectedMCPList.collectAsState().value
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LazyRow(modifier = Modifier.weight(1f)) {
                                    itemsIndexed(mcpList) { index, mcp ->
                                        Box(
                                            modifier = Modifier.background(
                                                if (mcpList.map { it.name }.intersect(selectedMCPList.map { it.name }).isNotEmpty()) {
                                                    ColorResource.theme
                                                } else {
                                                    ColorResource.white
                                                }, shape = RoundedCornerShape(4.dp)
                                            ).border(
                                                BorderStroke(
                                                    1.dp,
                                                    if (mcpList.map { it.name }.intersect(selectedMCPList.map { it.name }).isNotEmpty()) {
                                                        ColorResource.white
                                                    } else {
                                                        ColorResource.theme
                                                    }
                                                ), shape = RoundedCornerShape(4.dp)
                                            ).padding(horizontal = 10.dp, vertical = 4.dp).noRippleClickable {
                                                if (mcpList.map { it.name }.intersect(selectedMCPList.map { it.name }).isNotEmpty()) {
                                                    chatViewModel.selectedMCPList.value = chatViewModel.selectedMCPList.value - mcp
                                                } else {
                                                    chatViewModel.selectedMCPList.value = chatViewModel.selectedMCPList.value + mcp
                                                }
                                            }) {
                                            Text(
                                                mcp.name.orEmpty(), color =
                                                    if (mcpList.map { it.name }.intersect(selectedMCPList.map { it.name }).isNotEmpty()) {
                                                        ColorResource.white
                                                    } else {
                                                        ColorResource.theme
                                                    }, style = MaterialTheme.typography.body2
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                    }
                                }
                                Image(
                                    modifier = Modifier.size(12.dp).noRippleClickable {},
                                    contentDescription = null,
                                    painter = painterResource(resource = Res.drawable.ic_show_more),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        AppHorizontalDivider()
                        val chatList = chatViewModel.preChatMessage.collectAsState().value
                        val input = remember {
                            mutableStateOf("")
                        }
                        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            if (chatList.list.isNotEmpty()) {
                                ChatMessageList(
                                    messages = chatList.list, model = "De", modifier = Modifier.fillMaxWidth().weight(1f)
                                )
                            } else {
                                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp).weight(1f)) {
                                    Text("Recommended", modifier = Modifier, color = ColorResource.primaryText, style = MaterialTheme.typography.h2)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row {
                                        Box(
                                            modifier = Modifier.weight(1f).border(BorderStroke(1.dp, ColorResource.subText), shape = RoundedCornerShape(4.dp)).padding(horizontal = 16.dp, vertical = 16.dp)
                                                .noRippleClickable {
                                                    openURLByBrowser("https://modelcontextprotocol.io/introduction")
                                                }) {
                                            Column {
                                                Text("MCP Docs", color = ColorResource.primaryText, style = MaterialTheme.typography.body1)
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Text(
                                                    "Model Context Protocol\n" + "The Model Context Protocol (MCP) is an open protocol designed to facilitate seamless integration between Large Language Model (LLM) applications and external data sources and tools. It acts as a standardized connector, similar to a USB-C port, allowing AI models to easily access data and utilize various tools. MCP supports features such as data integration, tool integration, and secure two-way connections, enabling developers to build applications that can effectively communicate with different data sources.",
                                                    color = ColorResource.secondaryText,
                                                    style = MaterialTheme.typography.body2
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Box(
                                            modifier = Modifier.weight(1f).border(BorderStroke(1.dp, ColorResource.subText), shape = RoundedCornerShape(4.dp)).padding(horizontal = 16.dp, vertical = 16.dp)
                                                .clickUrl("https://api-docs.deepseek.com/")
                                        ) {
                                            Column {
                                                Text("Deepseek API Docs", color = ColorResource.primaryText, style = MaterialTheme.typography.body1)
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Text(
                                                    "DeepSeek，全称杭州深度求索人工智能基础技术研究有限公司。DeepSeek是一家创新型科技公，成立于2023年7月17日，使用数据蒸馏技术，得到更为精炼、有用的数据。由知名私募巨头幻方量化孕育而生，专注于开发先进的大语言模型（LLM）和相关技术。注册地址：浙江省杭州市拱墅区环城北路169号汇金国际大厦西1幢1201室。法定代表人为裴湉，经营范围包括技术服务、技术开发、软件开发等。",
                                                    color = ColorResource.secondaryText,
                                                    style = MaterialTheme.typography.body2
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                                        Text("There is nothing here.", modifier = Modifier.align(Alignment.Center), color = ColorResource.black20, style = MaterialTheme.typography.h3)
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().height(3.dp).shadow(
                                    Color(0x47000000),
                                    borderRadius = 0.dp,
                                    offsetX = 0.dp,
                                    offsetY = 0.dp,
                                    spread = 0.dp,
                                    blurRadius = 10.dp
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .background(ColorResource.white, RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                val click = remember {
                                    {
                                        val msg = input.value
                                        if (msg.isEmpty()) {
                                            screenNavigator.toast("Please enter a message")
                                        } else if (chatViewModel.lastChatResponse.value is ScreenResult.Loading) {
                                            screenNavigator.toast("Please wait a moment")
                                        } else {
                                            chatViewModel.chat(msg, selectedLLmConfigValue, selectedLLMModel.value.orEmpty(), mcpViewModel.getServer(), false) {
                                                mcpViewModel.callServer(it)
                                            }
                                            input.value = ""
                                        }
                                    }
                                }
                                AnimatedSelector(STREAM_LIST, DEFAULT_STREAM_MODEL) {
                                    chatViewModel.selectedStreamModel.value = it
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Box(
                                    modifier = Modifier.weight(1f).border(BorderStroke(1.dp, ColorResource.theme), shape = RoundedCornerShape(4.dp)),
                                ) {
                                    CustomEdit(
                                        input.value,
                                        hint = "Ask something...",
                                        textStyle = TextStyle.Default.copy(fontSize = 14.sp),
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                        onValueChange = {
                                            input.value = it
                                        },
                                        modifier = Modifier.onKeyEvent {
                                            if (it.key == Key.Enter) {
                                                click()
                                            }
                                            return@onKeyEvent false
                                        }.background(ColorResource.background).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                AppButton(
                                    modifier = Modifier, contentPaddingValues = PaddingValues(
                                        start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp
                                    ), {
                                        click()
                                    }, {
                                        Text(
                                            modifier = Modifier, text = "Send", color = ColorResource.white, fontSize = 10.sp
                                        )
                                    })
                            }
                        }
                    }
                } else {
                    LLMEmptyView()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMessageList(
    messages: List<ChatMessage>, model: String, modifier: Modifier = Modifier, onLoadMore: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    // 滚动到底部自动加载更多
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) onLoadMore()
    }

    LaunchedEffect(messages.lastOrNull()?.reasoningContent?.value) {
        listState.animateScrollBy(999f)
    }

    LaunchedEffect(messages.lastOrNull()?.content?.value) {
        listState.animateScrollBy(999f)
    }

    LazyColumn(
        state = listState, modifier = modifier
    ) {
        item { Spacer(Modifier.height(12.dp)) }
        items(messages.size, key = {
            messages[it].id
        }, contentType = { "message" } // 提升性能
        ) { index ->
            MessageItem(
                message = messages[index], model, modifier = Modifier.animateItemPlacement()
            )
        }
        item { Spacer(Modifier.height(12.dp)) }
    }
}

@Composable
private fun MessageItem(
    message: ChatMessage, model: String, modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!message.isFromMe) {
            Avatar(message.isFromMe, model)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start, modifier = Modifier.weight(1f, fill = false)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = message.createdTimeFormatted(), style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                )
                if (!message.isFromMe && !message.isLoading()) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_elapsed),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = message.elapsedTimeFormatted(), style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            ChatBubble(
                message = message, modifier = Modifier.widthIn(max = 600.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (!message.isFromMe && !message.isLoading()) {
                Row {
                    Image(
                        modifier = Modifier.size(12.dp),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_token_up),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "prompt ${message.promptTokens}", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_token_down),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "completion ${message.completionTokens}", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_token_total),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "total ${message.promptTokens + message.completionTokens}", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp).noClickableAndCopyStr(message.content.value, true),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_copy),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "copy", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier.noClickableAndCopyStr(message.content.value, true)
                    )
                }
            }
            if (message.isFromMe && !message.isLoading()) {
                Row {
                    Image(
                        modifier = Modifier.size(12.dp).noClickableAndCopyStr(message.content.value, true),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_copy),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "copy", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier.noClickableAndCopyStr(message.content.value, true)
                    )
                }
            }
        }
        if (message.isFromMe) {
            Spacer(modifier = Modifier.width(8.dp))
            Avatar(message.isFromMe, message.modelKey)
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
private fun ChatBubble(
    message: ChatMessage, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(8.dp)).border(BorderStroke(1.dp, ColorResource.black5), shape = RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        if (message.reasoningContent.value.isNotEmpty()) {
            Row(modifier = Modifier.drawWithContent {
                drawContent()
                drawLine(
                    color = ColorResource.subText.copy(0.6f),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }) {
                Spacer(modifier = Modifier.width(6.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = message.reasoningContent.value, style = MaterialTheme.typography.body1.copy(
                            color = ColorResource.secondaryText
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
        val toolCall = message.toolCall.value
        if (toolCall.isNotEmpty()) {
            Text(
                text = "Tools: $toolCall", style = MaterialTheme.typography.body1.copy(
                    color = ColorResource.theme
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message.content.value, style = MaterialTheme.typography.body1.copy(
                    color = ColorResource.primaryText
                )
            )
        } else {
            Text(
                text = message.content.value, style = MaterialTheme.typography.body1.copy(
                    color = ColorResource.primaryText
                )
            )
        }
    }
}

@Composable
private fun Avatar(isFromMe: Boolean, model: String) {
    val word = if (isFromMe) {
        "ME"
    } else {
        model.substring(0, 1)
    }
    Box(
        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(36.dp)).background(if (isFromMe) ColorResource.theme else ColorResource.chatAvatar)
    ) {
        Text(word, modifier = Modifier.align(Alignment.Center), color = ColorResource.white, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun AnimatedSelector(items: List<String>, default: String, select: (String) -> Unit) {
    val selectedIndex = remember { mutableStateOf(items.indexOf(default)) }
    val blockWidth = 68
    val offsetX = animateDpAsState(
        targetValue = with(LocalDensity.current) { (selectedIndex.value * blockWidth).dp }, label = "selectorAnimation"
    )
    Box(
        modifier = Modifier.background(ColorResource.background, RoundedCornerShape(4.dp))
    ) {
        // 背景滑动块
        Box(
            modifier = Modifier.offset(x = offsetX.value).size(blockWidth.dp, 26.dp).background(ColorResource.theme, RoundedCornerShape(4.dp)).animateContentSize()
        )
        // 选项布局
        Row {
            items.forEachIndexed { index, text ->
                Box(modifier = Modifier.size(blockWidth.dp, 24.dp).noRippleClickable {
                    selectedIndex.value = index
                    select.invoke(text)
                }.padding(4.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text, color = if (selectedIndex.value == index) {
                            Color.White
                        } else {
                            ColorResource.secondaryText
                        }, fontSize = 10.sp
                    )
                }
            }
        }
    }
}