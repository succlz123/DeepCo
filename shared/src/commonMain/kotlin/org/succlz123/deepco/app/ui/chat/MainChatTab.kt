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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_chat
import deep_co.shared.generated.resources.ic_copy
import deep_co.shared.generated.resources.ic_down
import deep_co.shared.generated.resources.ic_elapsed
import deep_co.shared.generated.resources.ic_model
import deep_co.shared.generated.resources.ic_prompt
import deep_co.shared.generated.resources.ic_qq
import deep_co.shared.generated.resources.ic_remove
import deep_co.shared.generated.resources.ic_send
import deep_co.shared.generated.resources.ic_show_more
import deep_co.shared.generated.resources.ic_speaker
import deep_co.shared.generated.resources.ic_token_down
import deep_co.shared.generated.resources.ic_token_total
import deep_co.shared.generated.resources.ic_token_up
import deep_co.shared.generated.resources.ic_tool
import deep_co.shared.generated.resources.logo_deepseek
import deep_co.shared.generated.resources.logo_google
import deep_co.shared.generated.resources.logo_grok
import deep_co.shared.generated.resources.logo_llm
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.Manifest
import org.succlz123.deepco.app.base.AppButton
import org.succlz123.deepco.app.base.AppDialogConfig
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppImageIconButton
import org.succlz123.deepco.app.base.AppImgButton
import org.succlz123.deepco.app.base.AppMessageDialog
import org.succlz123.deepco.app.base.CustomEdit
import org.succlz123.deepco.app.chat.msg.ChatMessage
import org.succlz123.deepco.app.chat.prompt.PromptInfo
import org.succlz123.deepco.app.chat.user.ChatUser
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.chat.MainChatViewModel.Companion.DEFAULT_INDEX_STREAM_MODEL
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel
import org.succlz123.deepco.app.ui.mcp.MainMcpViewModel
import org.succlz123.deepco.app.ui.prompt.MainPromptViewModel
import org.succlz123.deepco.app.ui.resize.PanelState
import org.succlz123.deepco.app.ui.resize.ResizablePanel
import org.succlz123.deepco.app.ui.resize.ResizablePanelTabView
import org.succlz123.deepco.app.ui.user.MainUserViewModel
import org.succlz123.deepco.app.ui.user.UserAvatarView
import org.succlz123.deepco.app.util.VerticalSplittable
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.click.onClickAndCopyStr
import org.succlz123.lib.click.onClickAndSpeakStr
import org.succlz123.lib.click.onClickUrl
import org.succlz123.lib.common.openURLByBrowser
import org.succlz123.lib.image.AsyncImageUrlMultiPlatform
import org.succlz123.lib.modifier.shadow
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.result.ScreenResult
import org.succlz123.lib.screen.viewmodel.globalViewModel


@Composable
fun MainChatTab(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(0.dp, 0.dp, 0.dp, 0.dp).fillMaxSize()) {
        DisableSelection {
            ChatView()
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

    val llmViewModel = globalViewModel { MainLLMViewModel() }
    val mcpViewModel = globalViewModel { MainMcpViewModel() }
    val promptViewModel = globalViewModel { MainPromptViewModel() }
    val mainChatViewModel = globalViewModel { MainChatViewModel() }
    val userViewModel = globalViewModel { MainUserViewModel() }
    val chatUser = userViewModel.chatUsers.collectAsState().value.find { it.isSelected }

    DisposableEffect(Unit) {
        onDispose {
            mainChatViewModel.clearIfNotDisplayed()
        }
    }
    val screenNavigator = LocalScreenNavigator.current
    val showHistoryRemoveDialog = remember {
        mutableStateOf(AppDialogConfig.DEFAULT)
    }
    Box(
        Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        VerticalSplittable(Modifier.fillMaxSize(), panelState.splitter, onResize = {
            panelState.expandedSize = (panelState.expandedSize + it).coerceAtLeast(panelState.expandedSizeMin)
        }) {
            ResizablePanel(Modifier.width(animatedSize).fillMaxHeight(), panelState) {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    ResizablePanelTabView(strings().history)
                    Spacer(Modifier.height(12.dp))
                    AppHorizontalDivider()
                    if (panelState.isExpanded) {
                        Spacer(Modifier.height(6.dp))
                        val h = mainChatViewModel.history.collectAsState().value
                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
                            items(h.entries.toList()) { item ->
                                val curSelect = mainChatViewModel.selectedHistory.collectAsState().value?.id
                                Text(
                                    item.value.list.firstOrNull()?.createTimeFormatted().orEmpty(),
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp),
                                    maxLines = 1,
                                    color = ColorResource.secondaryText,
                                    style = MaterialTheme.typography.overline
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        item.value.list.lastOrNull()?.content?.value.orEmpty(),
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f).noRippleClick {
                                            mainChatViewModel.updateHistory()
                                            mainChatViewModel.selectedHistory.value = item.value
                                            mainChatViewModel.preChatMessage.value = item.value
                                        }.background(if (curSelect == item.key) ColorResource.theme else ColorResource.transparent, shape = RoundedCornerShape(4.dp)).padding(vertical = 4.dp, horizontal = 6.dp),
                                        maxLines = 1,
                                        color = if (curSelect == item.key) ColorResource.white else ColorResource.primaryText,
                                        style = MaterialTheme.typography.body2
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Image(
                                        modifier = Modifier.size(14.dp).noRippleClick {
                                            showHistoryRemoveDialog.value = showHistoryRemoveDialog.value.copy(
                                                show = true,
                                                onPositiveClick = {
                                                    mainChatViewModel.removeHistory(item.key)
                                                }
                                            )
                                        },
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(ColorResource.error),
                                        painter = painterResource(resource = Res.drawable.ic_remove),
                                    )
                                    Spacer(Modifier.width(6.dp))
                                }
                            }
                        }
                    }
                }
            }
            Box() {
                val selectedLLMConfig = llmViewModel.selectedLLM.collectAsState()
                val selectedLLmConfigValue = selectedLLMConfig.value
                if (selectedLLmConfigValue != null) {
                    val selectedLLMModel = selectedLLmConfigValue.getSelectedModeMode()
                    Column(Modifier.fillMaxSize()) {
                        Row(Modifier.height(48.dp).fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                            val providerExpanded = remember {
                                mutableStateOf(false)
                            }
                            Row(
                                modifier = Modifier.background(ColorResource.theme.copy(0.1f), shape = RoundedCornerShape(4.dp)).padding(horizontal = 12.dp, vertical = 6.dp).noRippleClick {
                                    providerExpanded.value = true
                                }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                DropdownMenu(
                                    expanded = providerExpanded.value,
                                    modifier = Modifier,
                                    onDismissRequest = {},
                                    content = {
                                        llmViewModel.llmConfigs.value.forEach {
                                            DropdownMenuItem(contentPadding = PaddingValues(12.dp, 4.dp), onClick = {
                                                providerExpanded.value = !providerExpanded.value
                                                llmViewModel.saveDefaultLLM(it)
                                            }, content = {
                                                Text(text = it.provider + " " + it.name, color = ColorResource.secondaryText, style = MaterialTheme.typography.body2)
                                            })
                                        }
                                    },
                                )
                                val logo = remember(selectedLLmConfigValue.provider) {
                                    if (selectedLLmConfigValue.provider == MainLLMViewModel.PROVIDER_DEEPSEEK) {
                                        Res.drawable.logo_deepseek to 80.dp
                                    } else if (selectedLLmConfigValue.provider == MainLLMViewModel.PROVIDER_GEMINI) {
                                        Res.drawable.logo_google to 50.dp
                                    } else if (selectedLLmConfigValue.provider == MainLLMViewModel.PROVIDER_GROK) {
                                        Res.drawable.logo_grok to 38.dp
                                    } else {
                                        Res.drawable.logo_llm to 18.dp
                                    }
                                }
                                Image(
                                    painter = painterResource(resource = logo.first), contentDescription = "logo", modifier = Modifier.width(logo.second)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(selectedLLmConfigValue.name, color = ColorResource.theme, style = MaterialTheme.typography.body2)
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(resource = Res.drawable.ic_down),
                                    contentDescription = "down",
                                    colorFilter = ColorFilter.tint(ColorResource.theme),
                                    modifier = Modifier.width(10.dp).noRippleClick {})
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            val modeExpanded = remember {
                                mutableStateOf(false)
                            }
                            Row(
                                modifier = Modifier.background(ColorResource.theme.copy(0.1f), shape = RoundedCornerShape(4.dp)).padding(horizontal = 12.dp, vertical = 6.dp).noRippleClick {
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
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(selectedLLMModel, color = ColorResource.theme, style = MaterialTheme.typography.body2)
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(resource = Res.drawable.ic_down),
                                    contentDescription = "down",
                                    colorFilter = ColorFilter.tint(ColorResource.theme),
                                    modifier = Modifier.width(10.dp).noRippleClick {})
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            AppButton(
                                Modifier, contentPaddingValues = PaddingValues(
                                    start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp
                                ), onClick = {
                                    mainChatViewModel.clearIfNotDisplayed()
                                }, content = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            modifier = Modifier.size(14.dp),
                                            contentDescription = null,
                                            painter = painterResource(resource = Res.drawable.ic_chat),
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(strings().newChat, color = ColorResource.white, style = MaterialTheme.typography.body1)
                                    }
                                })
                        }
                        AppHorizontalDivider()
                        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(16.dp),
                                contentDescription = null,
                                painter = painterResource(resource = Res.drawable.ic_prompt),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            val prompts = promptViewModel.prompt.collectAsState().value.sortedByDescending { it.updateTime }
                            val selectedPrompt = mainChatViewModel.selectedPrompt.collectAsState().value
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LazyRow(modifier = Modifier.weight(1f)) {
                                    itemsIndexed(prompts) { index, prompt ->
                                        Box(
                                            modifier = Modifier.background(
                                                if (selectedPrompt.name == prompt.name) {
                                                    ColorResource.theme
                                                } else {
                                                    ColorResource.white
                                                }, shape = RoundedCornerShape(4.dp)
                                            ).border(
                                                BorderStroke(
                                                    1.dp, if (selectedPrompt.name == prompt.name) {
                                                        ColorResource.white
                                                    } else {
                                                        ColorResource.theme
                                                    }
                                                ), shape = RoundedCornerShape(4.dp)
                                            ).padding(horizontal = 10.dp, vertical = 4.dp).noRippleClick {
                                                mainChatViewModel.selectedPrompt.value = prompt
                                            }) {
                                            Text(
                                                prompt.name, color = if (selectedPrompt.name == prompt.name) {
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
                                    modifier = Modifier.size(16.dp).noRippleClick {},
                                    contentDescription = null,
                                    painter = painterResource(resource = Res.drawable.ic_show_more),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        AppHorizontalDivider()
                        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.size(16.dp),
                                contentDescription = null,
                                painter = painterResource(resource = Res.drawable.ic_tool),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            val mcpList = mcpViewModel.mcpList.collectAsState().value
                            val selectedMCPList = mainChatViewModel.selectedMCPList.collectAsState().value
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
                                            ).padding(horizontal = 10.dp, vertical = 4.dp).noRippleClick {
                                                if (mcpList.map { it.name }.intersect(selectedMCPList.map { it.name }).isNotEmpty()) {
                                                    mainChatViewModel.selectedMCPList.value = mainChatViewModel.selectedMCPList.value - mcp
                                                } else {
                                                    mainChatViewModel.selectedMCPList.value = mainChatViewModel.selectedMCPList.value + mcp
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
                                    modifier = Modifier.size(16.dp).noRippleClick {},
                                    contentDescription = null,
                                    painter = painterResource(resource = Res.drawable.ic_show_more),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        AppHorizontalDivider()
                        val chatList = mainChatViewModel.preChatMessage.collectAsState().value
                        val promptInfo = mainChatViewModel.selectedPrompt.collectAsState().value
                        val input = remember {
                            mutableStateOf("")
                        }
                        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            if (chatList.list.isNotEmpty()) {
                                ChatMessageList(
                                    messages = chatList.list, model = "", modifier = Modifier.fillMaxWidth().weight(1f), promptInfo = promptInfo, chatUser = chatUser
                                )
                            } else {
                                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp).weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(strings().recommend, modifier = Modifier, style = MaterialTheme.typography.h3)
                                        Spacer(modifier = Modifier.weight(1f))
                                        QQGroupView()
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    RecommendView()
                                }
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth().height(3.dp).shadow()
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .background(ColorResource.white, RoundedCornerShape(8.dp)).padding(horizontal = 16.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center, modifier = Modifier.noRippleClick {
                                        screenNavigator.push(Manifest.ChatUserSelectPopupScreen)
                                    }) {
                                    UserAvatarView(modifier = Modifier, 28.dp, chatUser?.avatar)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        if (!chatUser?.name.isNullOrEmpty()) {
                                            chatUser.name
                                        } else {
                                            "USER"
                                        }, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center, maxLines = 2, color = ColorResource.primaryText, fontSize = 8.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                val strings = strings()
                                val click = {
                                    val msg = input.value
                                    if (msg.isEmpty()) {
                                        screenNavigator.toast(strings.errorMessageIsEmpty)
                                    } else if (mainChatViewModel.lastChatResponse.value is ScreenResult.Loading) {
                                        screenNavigator.toast(strings.errorMessageIsBusy)
                                    } else {
                                        mainChatViewModel.chat(msg, selectedLLmConfigValue, mcpViewModel.getServer(), false, chatUser) {
                                            mcpViewModel.callServer(it)
                                        }
                                        input.value = ""
                                    }
                                }
                                AnimatedSelector(strings.chatNetworkModeList, DEFAULT_INDEX_STREAM_MODEL) {
                                    mainChatViewModel.selectedStreamModel.value = it
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Box(
                                    modifier = Modifier.weight(1f).border(BorderStroke(1.dp, ColorResource.border), shape = RoundedCornerShape(4.dp)),
                                ) {
                                    CustomEdit(
                                        input.value,
                                        hint = strings().askSomething,
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
                                Box() {
                                    Box(modifier = Modifier.padding(4.dp)) {
                                        AppImageIconButton(22.dp, ColorResource.theme, Res.drawable.ic_model) {
                                            screenNavigator.push(Manifest.ChatModeConfigPopupScreen)
                                        }
                                    }
                                    val chatModeConfig = mainChatViewModel.chatModeConfig.collectAsState().value
                                    if (chatModeConfig != mainChatViewModel.defaultChatModeConfig) {
                                        Box(modifier = Modifier.size(8.dp).background(ColorResource.red, RoundedCornerShape(16.dp)).align(Alignment.TopEnd)) {}
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                AppImgButton(drawable = Res.drawable.ic_send, text = strings().send, onClick = {
                                    click()
                                })
                            }
                        }
                    }
                } else {
                    LLMEmptyView()
                }
            }
        }
        AppMessageDialog(strings().tips, strings().tipsRemoveHistory, showHistoryRemoveDialog)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMessageList(
    messages: List<ChatMessage>, model: String, modifier: Modifier = Modifier, onLoadMore: () -> Unit = {}, promptInfo: PromptInfo?, chatUser: ChatUser?
) {
    val listState = rememberLazyListState()
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
        }, contentType = { "message" }
        ) { index ->
            MessageItem(
                message = messages[index], model, promptInfo, chatUser, modifier = Modifier.animateItemPlacement()
            )
        }
        item { Spacer(Modifier.height(12.dp)) }
    }
}

@Composable
private fun MessageItem(
    message: ChatMessage, model: String, promptInfo: PromptInfo?, chatUser: ChatUser?, modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!message.isFromMe) {
            Avatar(message.isFromMe, promptInfo, chatUser, model)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start, modifier = Modifier.weight(1f, fill = false)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = message.createTimeFormatted(), style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
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
                    if (message.promptTokens > 0) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            contentDescription = null,
                            painter = painterResource(resource = Res.drawable.ic_token_up),
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${strings().chatMessageIconPrompt} ${message.promptTokens}", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    if (message.completionTokens > 0) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            contentDescription = null,
                            painter = painterResource(resource = Res.drawable.ic_token_down),
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${strings().chatMessageIconCompletion} ${message.completionTokens}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    if ((message.promptTokens + message.completionTokens) > 0) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            contentDescription = null,
                            painter = painterResource(resource = Res.drawable.ic_token_total),
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${strings().chatMessageIconTotal} ${message.promptTokens + message.completionTokens}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    if (message.model.isNotEmpty()) {
                        Image(
                            modifier = Modifier.size(12.dp),
                            contentDescription = null,
                            painter = painterResource(resource = Res.drawable.ic_model),
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = message.model, style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f), modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Image(
                        modifier = Modifier.size(12.dp).onClickAndSpeakStr(message.content.value),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_speaker),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${strings().chatMessageIconSpeak}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.onClickAndSpeakStr(message.content.value)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp).onClickAndCopyStr(message.content.value, true),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_copy),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${strings().chatMessageIconCopy}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.onClickAndCopyStr(message.content.value, true)
                    )
                }
            }
            if (message.isFromMe && !message.isLoading()) {
                Row {
                    Image(
                        modifier = Modifier.size(12.dp).onClickAndSpeakStr(message.content.value),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_speaker),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${strings().chatMessageIconSpeak}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.onClickAndSpeakStr(message.content.value)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(12.dp).onClickAndCopyStr(message.content.value, true),
                        contentDescription = null,
                        painter = painterResource(resource = Res.drawable.ic_copy),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${strings().chatMessageIconCopy}",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.onClickAndCopyStr(message.content.value, true)
                    )
                }
            }
        }
        if (message.isFromMe) {
            Spacer(modifier = Modifier.width(8.dp))
            Avatar(message.isFromMe, promptInfo, chatUser, message.model)
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
private fun Avatar(isFromMe: Boolean, promptInfo: PromptInfo?, chatUser: ChatUser?, model: String) {
    val size = remember {
        48
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isFromMe) {
            UserAvatarView(Modifier, size.dp, chatUser?.avatar)
        } else {
            Box(
                modifier = Modifier.size(size.dp).clip(RoundedCornerShape(size.dp)).background(ColorResource.theme),
                contentAlignment = Alignment.Center
            ) {
                if (promptInfo?.avatar.isNullOrEmpty()) {
                    Text("L", modifier = Modifier.align(Alignment.Center), color = ColorResource.white, style = MaterialTheme.typography.subtitle1)
                } else {
                    AsyncImageUrlMultiPlatform(modifier = Modifier.size(size.dp), promptInfo.avatar)
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        if (isFromMe) {
            Text(
                if (!chatUser?.name.isNullOrEmpty()) {
                    chatUser.name
                } else {
                    "USER"
                }, modifier = Modifier.width((size - 4).dp), textAlign = TextAlign.Center, maxLines = 2, color = ColorResource.primaryText, fontSize = 10.sp
            )
        } else {
            Text(
                if (!promptInfo?.name.isNullOrEmpty()) {
                    promptInfo.name
                } else {
                    "DEFAULT"
                }, modifier = Modifier.width((size - 4).dp), textAlign = TextAlign.Center, maxLines = 2, color = ColorResource.primaryText, fontSize = 10.sp
            )
        }
    }
}

@Composable
fun AnimatedSelector(items: List<String>, default: Int, select: (Int) -> Unit) {
    val selectedIndex = remember { mutableStateOf(default) }
    val blockWidth = 58
    val offsetX = animateDpAsState(
        targetValue = with(LocalDensity.current) { (selectedIndex.value * blockWidth).dp }, label = "selectorAnimation"
    )
    Box(
        modifier = Modifier.background(ColorResource.background, RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier.offset(x = offsetX.value).size(blockWidth.dp, 26.dp).background(ColorResource.theme, RoundedCornerShape(4.dp)).animateContentSize()
        )
        Row {
            items.forEachIndexed { index, text ->
                Box(modifier = Modifier.size(blockWidth.dp, 24.dp).noRippleClick {
                    selectedIndex.value = index
                    select.invoke(index)
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

@Composable
fun QQGroupView() {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(ColorResource.orangeLight, RoundedCornerShape(26.dp))
            .border(BorderStroke(1.dp, ColorResource.orange), RoundedCornerShape(26.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorResource.orange),
            painter = painterResource(resource = Res.drawable.ic_qq),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(strings().joinQQGroup, modifier = Modifier.onClickAndCopyStr("681703796", true), style = MaterialTheme.typography.caption.copy(color = ColorResource.orange))
    }
}

@Composable
fun RecommendView() {
    Row {
        Box(
            modifier = Modifier.weight(1f).background(ColorResource.background, shape = RoundedCornerShape(4.dp)).padding(horizontal = 16.dp, vertical = 16.dp)
                .noRippleClick {
                    openURLByBrowser("https://modelcontextprotocol.io/introduction")
                }) {
            Column {
                Text("MCP Docs", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Model Context Protocol\n" + "The Model Context Protocol (MCP) is an open protocol designed to facilitate seamless integration between Large Language Model (LLM) applications and external data sources and tools. It acts as a standardized connector, similar to a USB-C port, allowing AI models to easily access data and utilize various tools. MCP supports features such as data integration, tool integration, and secure two-way connections, enabling developers to build applications that can effectively communicate with different data sources.",
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier.weight(1f).background(ColorResource.background, shape = RoundedCornerShape(4.dp)).padding(horizontal = 16.dp, vertical = 16.dp)
                .onClickUrl("https://api-docs.deepseek.com/")
        ) {
            Column {
                Text("Deepseek API Docs", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "DeepSeek，全称杭州深度求索人工智能基础技术研究有限公司。DeepSeek是一家创新型科技公，成立于2023年7月17日，使用数据蒸馏技术，得到更为精炼、有用的数据。由知名私募巨头幻方量化孕育而生，专注于开发先进的大语言模型（LLM）和相关技术。注册地址：浙江省杭州市拱墅区环城北路169号汇金国际大厦西1幢1201室。法定代表人为裴湉，经营范围包括技术服务、技术开发、软件开发等。",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}