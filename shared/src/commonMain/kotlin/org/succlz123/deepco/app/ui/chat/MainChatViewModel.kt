package org.succlz123.deepco.app.ui.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.succlz123.deepco.app.base.BaseBizViewModel
import org.succlz123.deepco.app.base.DIR_HISTORY
import org.succlz123.deepco.app.base.JSON_CHAT_MODE
import org.succlz123.deepco.app.base.LocalDirStorage
import org.succlz123.deepco.app.base.LocalStorage
import org.succlz123.deepco.app.chat.msg.ChatMessage
import org.succlz123.deepco.app.chat.msg.ChatMessage.Companion.TYPE_LOADING
import org.succlz123.deepco.app.chat.msg.ChatMessageData
import org.succlz123.deepco.app.chat.msg.ChatMessageData.Companion.NON_ID
import org.succlz123.deepco.app.chat.prompt.PromptInfo
import org.succlz123.deepco.app.chat.role.ChatRoleDefine
import org.succlz123.deepco.app.chat.user.ChatUser
import org.succlz123.deepco.app.llm.ChatResponse
import org.succlz123.deepco.app.llm.deepseek.DeepSeekApiService
import org.succlz123.deepco.app.llm.deepseek.ToolCall
import org.succlz123.deepco.app.mcp.biz.McpConfig
import org.succlz123.deepco.app.mcp.biz.Tool
import org.succlz123.deepco.app.mcp.biz.ToolUse
import org.succlz123.deepco.app.ui.llm.LLM
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel.Companion.PROVIDER_GEMINI
import org.succlz123.deepco.app.ui.llm.MainLLMViewModel.Companion.PROVIDER_GROK
import org.succlz123.lib.logger.Logger
import org.succlz123.lib.screen.result.ScreenResult
import kotlin.random.Random

class MainChatViewModel : BaseBizViewModel() {

    companion object {

        val STREAM_LIST = listOf("stream", "complete")

        val DEFAULT_STREAM_MODEL = "stream"
    }

    val chatModeLocalStorage = LocalStorage(JSON_CHAT_MODE)

    val chatLocalStorage = LocalDirStorage(DIR_HISTORY)

    val history = MutableStateFlow<Map<Long, ChatMessageData>>(localHistory().orEmpty())

    val selectedHistory = MutableStateFlow<ChatMessageData?>(null)

    val selectedStreamModel = MutableStateFlow<String>(DEFAULT_STREAM_MODEL)

    val selectedPrompt = MutableStateFlow<PromptInfo>(ChatRoleDefine.roles.first())

    val selectedMCPList = MutableStateFlow<List<McpConfig>>(emptyList())

    val defaultChatModeConfig = ChatModeConfig()

    val chatModeConfig = MutableStateFlow<ChatModeConfig>(chatModeLocalStorage.get() ?: defaultChatModeConfig)

    val preChatMessage = MutableStateFlow<ChatMessageData>(ChatMessageData())

    val lastChatResponse = MutableStateFlow<ScreenResult<Int>>(ScreenResult.Uninitialized)

    fun clearIfNotDisplayed() {
        updateHistory()
        selectedHistory.value = null
        preChatMessage.value = ChatMessageData()
        lastChatResponse.value = ScreenResult.Uninitialized
    }

    fun localHistory(): HashMap<Long, ChatMessageData>? {
        val map = hashMapOf<Long, ChatMessageData>()
        val chats = chatLocalStorage.getAllFileDir<ChatMessageData>().orEmpty()
        for (data in chats) {
            map[data.id] = data
        }
        return map
    }

    fun updateHistory() {
        if (preChatMessage.value.list.isNotEmpty()) {
            history.value = history.value.toMutableMap().apply {
                val nonLoading = preChatMessage.value.list.filter { !it.isLoading() }
                put(preChatMessage.value.id, preChatMessage.value.copy(list = nonLoading))
            }
            chatLocalStorage.put(preChatMessage.value, preChatMessage.value.id.toString())
        }
    }

    fun removeHistory(messagesId: Long) {
        selectedHistory.value = null
        history.value = history.value.toMutableMap().apply {
            remove(messagesId)
        }
        if (preChatMessage.value.id == messagesId) {
            preChatMessage.value = ChatMessageData()
        }
        chatLocalStorage.remove(messagesId.toString())
    }

    fun saveChatModeConfig() {
        chatModeLocalStorage.put(chatModeConfig.value)
    }

    fun chat(chatText: String, llm: LLM, mcpList: Map<String, List<Tool>>, manuallyClear: Boolean, chatUser: ChatUser?, toolsCallCb: suspend (List<ToolCall>) -> List<ToolUse>?) {
        if (lastChatResponse.value is ScreenResult.Loading) {
            return
        }
        val loadingMessage = ChatMessage(
            id = TYPE_LOADING,
            isFromMe = false
        ).apply {
            changeContent("Thinking...")
        }

        preChatMessage.value = preChatMessage.value.copy(
            list = preChatMessage.value.list.toMutableList().apply {
                add(ChatMessage(model = llm.getSelectedModeMode(), isFromMe = true).apply {
                    changeContent(chatText)
                })
                add(loadingMessage)
            }, id = if (preChatMessage.value.id == NON_ID) {
                System.currentTimeMillis()
            } else {
                preChatMessage.value.id
            }
        )
        lastChatResponse.value = ScreenResult.Loading()
        val user = if (chatUser == null || chatUser.isDefault) {
            ""
        } else {
            "{\"user\":\"${chatUser.name}\", \"userDescription\":\"${chatUser.description}\"}"
        }
        val prompt = selectedPrompt.value.description + "\n\n\n" + user
        val mcp = selectedMCPList.value
        val tools = mcpList.filter { server -> mcp.find { inner -> inner.name == server.key } != null }.values.toList()
        val content = if (manuallyClear) {
            emptyList()
        } else {
            preChatMessage.value.list
        }
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val stream = selectedStreamModel.value == "stream"
                val startTime = System.currentTimeMillis()
                val contentSb = StringBuilder()
                val reasoningContentSb = StringBuilder()
                if (llm.provider == MainLLMViewModel.PROVIDER_DEEPSEEK) {
                    var reCall = true
                    var toolUseResult: List<ToolUse>? = null
                    while (reCall) {
                        reCall = false
                        DeepSeekApiService.chat(
                            llm.apiKey, llm.getSelectedModeMode(), stream, tools, toolUseResult, prompt, chatModeConfig.value, content, { response, isStop ->
                                if (loadingMessage.id == TYPE_LOADING) {
                                    loadingMessage.id = System.nanoTime()
                                }
                                if (!isStop) {
                                    if (response.errorMsg.isEmpty()) {
                                        reasoningContentSb.append(response.choices?.firstOrNull()?.delta?.reasoning_content.orEmpty())
                                        contentSb.append(response.choices?.firstOrNull()?.delta?.content.orEmpty())
                                    } else {
                                        contentSb.append(response.errorMsg)
                                    }
                                    loadingMessage.changeReasoningContent(reasoningContentSb.toString())
                                    loadingMessage.changeContent(contentSb.toString())
                                } else {
                                    if (response.errorMsg.isEmpty()) {
                                        reasoningContentSb.append(response.choices?.firstOrNull()?.message?.reasoning_content.orEmpty())
                                        if (!stream) {
                                            contentSb.append(response.choices?.firstOrNull()?.message?.content.orEmpty())
                                        }
                                    } else {
                                        contentSb.append(response.errorMsg)
                                    }

                                    loadingMessage.promptTokens = response.usage?.prompt_tokens ?: 0
                                    loadingMessage.completionTokens = response.usage?.completion_tokens ?: 0
                                    loadingMessage.elapsedTime = System.currentTimeMillis() - startTime
                                    loadingMessage.model = response.model.orEmpty()

                                    loadingMessage.changeReasoningContent(reasoningContentSb.toString())
                                    loadingMessage.changeContent(contentSb.toString())

                                    val apiToolCalls = response.choices?.firstOrNull()?.message?.tool_calls
                                    if (!apiToolCalls.isNullOrEmpty()) {
                                        loadingMessage.changeToolCall(apiToolCalls.joinToString() { it.function?.name.orEmpty() })
                                        toolUseResult = toolsCallCb.invoke(apiToolCalls)
                                        Logger.log("Tool Use Result: ${toolUseResult?.joinToString()}")
                                        reCall = true
//                                    preChatMessage.value = preChatMessage.value.copy(
//                                        list = preChatMessage.value.list.toMutableList().apply {
//                                            add(ChatMessage(modelKey = model, isFromMe = true).apply {
//                                                changeContent(toolUse.orEmpty().joinToString() {
//                                                    "Tool Name: ${it.toolName}\nTool Result: ${it.toolResult}"
//                                                })
//                                            })
//                                        }
//                                    )
                                    }
                                    lastChatResponse.value = ScreenResult.Success(Random.nextInt())
                                }
                            }
                        )
                        if (reCall) {
                            Logger.log("Call Next")
                        }
                    }
                } else if (llm.provider == PROVIDER_GEMINI || llm.provider == PROVIDER_GROK) {
                    if (loadingMessage.id == TYPE_LOADING) {
                        loadingMessage.id = System.nanoTime()
                    }
                    if (stream) {
                        val streamCb = { response: ChatResponse, isStop: Boolean ->
                            if (!isStop) {
                                if (response.errorMsg.isNullOrEmpty()) {
                                    reasoningContentSb.append(response.thinking.orEmpty())
                                    contentSb.append(response.text.orEmpty())
                                } else {
                                    contentSb.append(response.errorMsg.orEmpty())
                                }
                            } else {
                                loadingMessage.elapsedTime = System.currentTimeMillis() - startTime
                                if (!response.errorMsg.isNullOrEmpty()) {
                                    contentSb.append(response.errorMsg.orEmpty())
                                    lastChatResponse.value = ScreenResult.Fail(Exception(response.errorMsg))
                                } else {
                                    lastChatResponse.value = ScreenResult.Success(Random.nextInt())
                                }
                            }
                            loadingMessage.model = response.model.orEmpty()
                            loadingMessage.changeReasoningContent(reasoningContentSb.toString().trim())
                            loadingMessage.changeContent(contentSb.toString().trim())
                        }
                        if (llm.provider == PROVIDER_GEMINI) {
                            org.succlz123.deepco.app.llm.gemini.geminiChatStream(llm.apiKey, llm.getSelectedModeMode(), prompt, chatModeConfig.value, content, streamCb)
                        } else {
                            org.succlz123.deepco.app.llm.grok.grokChatStream(llm.apiKey, llm.getSelectedModeMode(), prompt, chatModeConfig.value, content, streamCb)
                        }
                    } else {
                        val response = if (llm.provider == PROVIDER_GEMINI) {
                            org.succlz123.deepco.app.llm.gemini.geminiChat(llm.apiKey, llm.getSelectedModeMode(), prompt, chatModeConfig.value, content)
                        } else {
                            org.succlz123.deepco.app.llm.grok.grokChat(llm.apiKey, llm.getSelectedModeMode(), prompt, chatModeConfig.value, content)
                        }
                        loadingMessage.elapsedTime = System.currentTimeMillis() - startTime
                        loadingMessage.model = response.model.orEmpty()
                        if (response.errorMsg.isNullOrEmpty()) {
                            loadingMessage.changeReasoningContent(response.thinking?.trim().orEmpty())
                            loadingMessage.changeContent(response.text?.trim().orEmpty())
                            lastChatResponse.value = ScreenResult.Success(Random.nextInt())
                        } else {
                            loadingMessage.changeContent(response.errorMsg.orEmpty())
                            lastChatResponse.value = ScreenResult.Fail(Exception(response.errorMsg))
                        }
                    }
                } else {
                    lastChatResponse.value = ScreenResult.Fail(Exception("Not support provider"))
                    if (loadingMessage.id == TYPE_LOADING) {
                        loadingMessage.id = System.nanoTime()
                    }
                    loadingMessage.changeContent("Not support provider")
                }
            }
        }
    }
}