package org.succlz123.deepco.app.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.succlz123.deepco.app.ui.setting.MainSettingViewModel

enum class LocaleLanguage {
    EN,
    ZH
}

//private fun getLanguageFromSysDefault(): LocaleLanguage {
//    return when (Locale.getDefault().language) {
//        "en" -> LocaleLanguage.EN
//        "zh" -> LocaleLanguage.ZH
//        "ja" -> LocaleLanguage.JA
//        else -> LocaleLanguage.EN
//    }
//}

@Composable
fun strings(): Strings {
    val lan = remember {
        language
    }
    return when (lan.value) {
        LocaleLanguage.EN -> EnStrings
        LocaleLanguage.ZH -> ZhStrings
        else -> EnStrings
    }
}

var language = mutableStateOf(MainSettingViewModel.getLocaleLanguage())

fun languageToEN() {
    language.value = LocaleLanguage.EN
}

fun languageToZH() {
    language.value = LocaleLanguage.ZH
}

interface Strings {
    val confirm: String
    val ok: String
    val cancel: String
    val send: String
    val exit: String
    val loadingFailed: String
    val retry: String
    val copied: String
    val speaking: String
    val llm: String
    val language: String
    val settingLanguage: String
    val settingLanguageList: List<String>
    val setting: String
    val localConfigFile: String
    val openLocalConfigDir: String
    val declaration: String
    val declarationContent: String
    val github: String
    val deviceInfo: String
    val version: String
    val platform: String
    val cpuSize: String
    val os: String
    val mcpToolExecutionMode: String
    val mcpToolsMode: List<String>
    val tabSetting: String
    val tabMCP: String
    val tabUser: String
    val tabPrompt: String
    val tabLLM: String
    val tabChat: String
    val envInfo: String
    val mcpServersCollection: String
    val localMCPServers: String
    val mcpConfig: String
    val name: String
    val description: String
    val type: String
    val command: String
    val args: String
    val enable: String
    val chatUser: String
    val selectType: String
    val tips: String
    val tipsRemoveUser: String
    val tipsRemoveHistory: String
    val tipsRemovePrompt: String
    val selectAChatUser: String
    val chatUserConfig: String
    val errorNameIsEmpty: String
    val errorNameIsDuplicate: String
    val errorDescriptionIsEmpty: String
    val errorBaseApiUrlIsEmpty: String
    val errorApiKeyIsEmpty: String
    val errorProviderIsEmpty: String
    val chatNetworkModeList: List<String>
    val joinQQGroup: String
    val newChat: String
    val promptTitle: String
    val tavernCardWebsite: String
    val promptList: String
    val promptConfiguration: String
    val askSomething: String
    val history: String
    val recommend: String
    val errorMessageIsEmpty: String
    val errorMessageIsBusy: String
    val chatMessageIconPrompt: String
    val chatMessageIconCompletion: String
    val chatMessageIconTotal: String
    val chatMessageIconCopy: String
    val chatMessageIconSpeak: String
    val errorLLMProviderIsEmpty: String
    val myLLM: String
    val llmConfiguration: String
    val provider: String
    val selectProvider: String
    val model: String
    val operation: String
    val createdTime: String
    val baseAPIUrl: String
    val apiKey: String
    val currentSupportLLM: String
    val tipsRemoveLLM: String
    val chatConfiguration: String
    val default: String
    val reset: String
    val maxOutputTokens: String
    val maxOutputTokensDescription: String
    val temperature: String
    val temperatureDescription: String
    val topP: String
    val topPDescription: String
    val topK: String
    val topKDescription: String
    val frequencyPenalty: String
    val frequencyPenaltyDescription: String
    val presencePenalty: String
    val presencePenaltyDescription: String
    val importTavernV2Card: String
    val errorImportTavernV2CardNoneFile: String
    val errorImportTavernV2CardFileInvalidation: String
    val errorImportTavernV2CardContentInvalidation: String
    val errorPromptInfoIsNull: String
}

object EnStrings : Strings {
    override val confirm: String
        get() = "Confirm"
    override val ok: String
        get() = "OK"
    override val cancel: String
        get() = "Cancel"
    override val send: String
        get() = "Send"
    override val exit: String
        get() = "Exit"
    override val loadingFailed: String
        get() = "Loading Failed!"
    override val retry: String
        get() = "Retry"
    override val copied: String
        get() = "Copied"
    override val speaking: String
        get() = "Speaking..."
    override val llm: String
        get() = "LLM"
    override val language: String
        get() = "Language"
    override val settingLanguage: String
        get() = "Client Language"
    override val settingLanguageList: List<String>
        get() = listOf("Chinese", "English")
    override val setting: String
        get() = "Setting"
    override val localConfigFile: String
        get() = "Local Config File"
    override val openLocalConfigDir: String
        get() = "Open Local Config Dir"
    override val declaration: String
        get() = "Declaration"
    override val declarationContent: String
        get() = "As an LLM Client, all internal resources come from the Internet. This application is only for sharing and will not save any user-related information at present."
    override val github: String
        get() = "Github"
    override val deviceInfo: String
        get() = "Device Info"
    override val version: String
        get() = "Version"
    override val platform: String
        get() = "Platform"
    override val cpuSize: String
        get() = "Cpu Size"
    override val os: String
        get() = "OS"
    override val mcpToolExecutionMode: String
        get() = "MCP tool execution mode: "
    override val mcpToolsMode: List<String>
        get() = listOf("Automatic", "Manual")
    override val tabSetting: String
        get() = "SET"
    override val tabMCP: String
        get() = "MCP"
    override val tabUser: String
        get() = "User"
    override val tabPrompt: String
        get() = "Prompt"
    override val tabLLM: String
        get() = "LLM"
    override val tabChat: String
        get() = "Chat"
    override val envInfo: String
        get() = "ENV Info"
    override val mcpServersCollection: String
        get() = "MCP Servers Collection"
    override val localMCPServers: String
        get() = "Local MCPServers"
    override val mcpConfig: String
        get() = "MCP Configuration"
    override val name: String
        get() = "Name"
    override val description: String
        get() = "Description"
    override val type: String
        get() = "Type"
    override val command: String
        get() = "Commend"
    override val args: String
        get() = "Args"
    override val enable: String
        get() = "Enable"
    override val chatUser: String
        get() = "Chat User"
    override val selectType: String
        get() = "Select Type"
    override val tips: String
        get() = "Tips"
    override val tipsRemoveUser: String
        get() = "Are you sure to remove this user?"
    override val tipsRemoveHistory: String
        get() = "Are you sure to remove this history?"
    override val tipsRemovePrompt: String
        get() = "Are you sure to remove this prompt?"
    override val selectAChatUser: String
        get() = "Select A Chat User"
    override val chatUserConfig: String
        get() = "Chat User Config"
    override val errorNameIsEmpty: String
        get() = "Name is empty!"
    override val errorNameIsDuplicate: String
        get() = "Name is duplicate!"
    override val errorDescriptionIsEmpty: String
        get() = "Description is empty!"
    override val errorBaseApiUrlIsEmpty: String
        get() = "Base API Url is empty!"
    override val errorApiKeyIsEmpty: String
        get() = "API Key is empty!"
    override val errorProviderIsEmpty: String
        get() = "Provider is empty!"
    override val chatNetworkModeList: List<String>
        get() = listOf("stream", "complete")
    override val joinQQGroup: String
        get() = "Join QQ Group 681703796"
    override val newChat: String
        get() = "New Chat"
    override val promptTitle: String
        get() = "Prompt"
    override val tavernCardWebsite: String
        get() = "Tavern Card Website"
    override val promptList: String
        get() = "Prompt List"
    override val promptConfiguration: String
        get() = "Prompt Configuration"
    override val askSomething: String
        get() = "Ask something..."
    override val history: String
        get() = "History"
    override val recommend: String
        get() = "Recommend"
    override val errorMessageIsEmpty: String
        get() = "Please enter a message!"
    override val errorMessageIsBusy: String
        get() = "Please wait a moment!"
    override val chatMessageIconPrompt: String
        get() = "prompt"
    override val chatMessageIconCompletion: String
        get() = "completion"
    override val chatMessageIconTotal: String
        get() = "total"
    override val chatMessageIconCopy: String
        get() = "copy"
    override val chatMessageIconSpeak: String
        get() = "speak"
    override val errorLLMProviderIsEmpty: String
        get() = "Please config a LLM Provider first!"
    override val myLLM: String
        get() = "My LLM"
    override val llmConfiguration: String
        get() = "LLM Configuration"
    override val provider: String
        get() = "Provider"
    override val selectProvider: String
        get() = "Select Provider"
    override val model: String
        get() = "Model"
    override val operation: String
        get() = "Operation"
    override val createdTime: String
        get() = "Created Time"
    override val baseAPIUrl: String
        get() = "Base API Url"
    override val apiKey: String
        get() = "API Key"
    override val currentSupportLLM: String
        get() = "Current support DeepSeek / Grok / Gemini"
    override val tipsRemoveLLM: String
        get() = "Are you sure to remove this configuration?"
    override val chatConfiguration: String
        get() = "Chat Configuration"
    override val default: String
        get() = "Default"
    override val reset: String
        get() = "rest"
    override val maxOutputTokens: String
        get() = "MaxOutputTokens"
    override val maxOutputTokensDescription: String
        get() = "An integer between 1 and 8192 that limits the maximum number of tokens the model can generate for completion in a single request. The total length of input and output tokens is bounded by the context length of the model."
    override val temperature: String
        get() = "Temperature"
    override val temperatureDescription: String
        get() = "Temperature, between 0 and 2. Higher values, such as 0.8, make the output more random, while lower values, such as 0.2, make it more focused and deterministic. It is generally recommended that you change either this value or top_p, but not both."
    override val topP: String
        get() = "TopP"
    override val topPDescription: String
        get() = "As an alternative to conditioning the sampling temperature, the model takes into account the result of the token of the previous top_p probability. So 0.1 means that only tokens included in the top 10% probability will be considered. It is generally recommended to change either the value or the temperature, but not both."
    override val topK: String
        get() = "TopK"
    override val topKDescription: String
        get() = "Changing how the model selects output lemmas If topK is set to 1, it means that the selected token is the token with the highest probability among all tokens in the model vocabulary; If topK is set to 3, it means that the system will select the next token from the 3 tokens with the highest probability (determined by temperature). The system will further filter the lemmas based on topP and select the final lemmas using temperature sampling."
    override val frequencyPenalty: String
        get() = "FrequencyPenalty"
    override val frequencyPenaltyDescription: String
        get() = "A number between -2.0 and 2.0. If the value is positive, then the new token is penalized based on its frequency in the existing text, reducing the likelihood that the model will repeat the same content. The positive penalty prevents the use of tokens that have been used in the answer, thus increasing the vocabulary. A negative penalty encourages the use of tokens that have been used in the response, thus reducing the vocabulary size."
    override val presencePenalty: String
        get() = "PresencePenalty"
    override val presencePenaltyDescription: String
        get() = "A number between -2.0 and 2.0. If the value is positive, the new token is penalized based on whether it already appears in the existing text, increasing the likelihood that the model will talk about the new topic. A positive penalty prevents the use of a used token, and the strength of the penalty is proportional to the number of times a token is used: the more times a token is used, the harder it is for the model to use that token again to increase the vocabulary of the answer. Note: A negative penalty encourages the model to reuse tokens based on how many times they are used. A small negative value reduces the vocabulary of the response. The larger the negative value, the model will start repeating a common token until the maxOutputTokens limit is reached."
    override val importTavernV2Card: String
        get() = "Import\nTavern V2 Card\n(PNG/JSON)"
    override val errorImportTavernV2CardNoneFile: String
        get() = "Please chose a file to import!"
    override val errorImportTavernV2CardFileInvalidation: String
        get() = "Please chose the png or json file!"
    override val errorImportTavernV2CardContentInvalidation: String
        get() = "Please chose the Tavern V2 Card file!"
    override val errorPromptInfoIsNull: String
        get() = "Prompt info is null!"
}

object ZhStrings : Strings {
    override val confirm: String
        get() = "确认"
    override val ok: String
        get() = "确认"
    override val cancel: String
        get() = "取消"
    override val send: String
        get() = "发送"
    override val exit: String
        get() = "退出"
    override val loadingFailed: String
        get() = "数据加载失败!"
    override val retry: String
        get() = "重试'"
    override val copied: String
        get() = "复制"
    override val speaking: String
        get() = "朗读中..."
    override val llm: String
        get() = "大模型"
    override val language: String
        get() = "语言"
    override val settingLanguage: String
        get() = "客户端语言"
    override val settingLanguageList: List<String>
        get() = listOf("中文", "英文")
    override val setting: String
        get() = "设置"
    override val localConfigFile: String
        get() = "本地配置文件"
    override val openLocalConfigDir: String
        get() = "打开本地配置文件目录"
    override val declaration: String
        get() = "声明"
    override val declarationContent: String
        get() = "作为一个大模型本地应用，该客户端所有的内部资源都来自互联网。此应用程序仅供分享，目前不会保存任何用户相关信息。"
    override val github: String
        get() = "Github"
    override val deviceInfo: String
        get() = "设备信息"
    override val version: String
        get() = "版本"
    override val platform: String
        get() = "平台"
    override val cpuSize: String
        get() = "处理器核心数"
    override val os: String
        get() = "系统"
    override val mcpToolExecutionMode: String
        get() = "MCP 工具调用确认模式: "
    override val mcpToolsMode: List<String>
        get() = listOf("自动", "手动")
    override val tabSetting: String
        get() = "设置"
    override val tabMCP: String
        get() = "MCP"
    override val tabUser: String
        get() = "用户"
    override val tabPrompt: String
        get() = "提示词"
    override val tabLLM: String
        get() = "大模型"
    override val tabChat: String
        get() = "对话"
    override val envInfo: String
        get() = "环境信息"
    override val mcpServersCollection: String
        get() = "MCP 服务集合网站"
    override val localMCPServers: String
        get() = "本地 MCP"
    override val mcpConfig: String
        get() = "MCP 配置"
    override val name: String
        get() = "名字"
    override val description: String
        get() = "描述"
    override val type: String
        get() = "类型"
    override val command: String
        get() = "命令"
    override val args: String
        get() = "参数"
    override val enable: String
        get() = "启用"
    override val chatUser: String
        get() = "对话角色"
    override val selectType: String
        get() = "选择类型"
    override val tips: String
        get() = "提示"
    override val tipsRemoveUser: String
        get() = "确认要删除该角色?"
    override val tipsRemoveHistory: String
        get() = "确认要删除该对话历史记录?"
    override val tipsRemovePrompt: String
        get() = "确认要删除该对提示词?"
    override val selectAChatUser: String
        get() = "请选择一个角色进行对话"
    override val chatUserConfig: String
        get() = "对话角色配置"
    override val errorNameIsEmpty: String
        get() = "名字为空!"
    override val errorNameIsDuplicate: String
        get() = "名字重复!"
    override val errorDescriptionIsEmpty: String
        get() = "描述为空!"
    override val errorBaseApiUrlIsEmpty: String
        get() = "基础请求地址为空!"
    override val errorApiKeyIsEmpty: String
        get() = "API 秘钥为空!"
    override val errorProviderIsEmpty: String
        get() = "服务商为空!"
    override val chatNetworkModeList: List<String>
        get() = listOf("流式", "完整")
    override val joinQQGroup: String
        get() = "加入QQ群 681703796"
    override val newChat: String
        get() = "新对话"
    override val promptTitle: String
        get() = "提示词"
    override val tavernCardWebsite: String
        get() = "Tavern 酒馆卡片网站"
    override val promptList: String
        get() = "提示词列表"
    override val promptConfiguration: String
        get() = "提示词配置"
    override val askSomething: String
        get() = "提问..."
    override val history: String
        get() = "历史"
    override val recommend: String
        get() = "推荐"
    override val errorMessageIsEmpty: String
        get() = "请输入消息!"
    override val errorMessageIsBusy: String
        get() = "对话忙, 请稍等会儿!"
    override val chatMessageIconPrompt: String
        get() = "提示词"
    override val chatMessageIconCompletion: String
        get() = "回复"
    override val chatMessageIconTotal: String
        get() = "总共"
    override val chatMessageIconCopy: String
        get() = "复制"
    override val chatMessageIconSpeak: String
        get() = "朗读"
    override val errorLLMProviderIsEmpty: String
        get() = "请先配置一个大模型提供商!"
    override val myLLM: String
        get() = "我的大模型"
    override val llmConfiguration: String
        get() = "大模型配置"
    override val provider: String
        get() = "服务商"
    override val selectProvider: String
        get() = "选择服务商"
    override val model: String
        get() = "模型"
    override val operation: String
        get() = "操作"
    override val createdTime: String
        get() = "创建时间"
    override val baseAPIUrl: String
        get() = "基础请求地址"
    override val apiKey: String
        get() = "API 秘钥"
    override val currentSupportLLM: String
        get() = "当前支持 DeepSeek / Grok / Gemini 大模型"
    override val tipsRemoveLLM: String
        get() = "确认删除当前大模型配置吗?"
    override val chatConfiguration: String
        get() = "对话配置"
    override val default: String
        get() = "默认"
    override val reset: String
        get() = "重置"
    override val maxOutputTokens: String
        get() = "最大输出 token 数"
    override val maxOutputTokensDescription: String
        get() = "介于 1 到 8192 间的整数，限制一次请求中模型生成 completion 的最大 token 数。输入 token 和输出 token 的总长度受模型的上下文长度的限制。"
    override val temperature: String
        get() = "采样温度"
    override val temperatureDescription: String
        get() = "采样温度，介于 0 和 2 之间。更高的值，如 0.8，会使输出更随机，而更低的值，如 0.2，会使其更加集中和确定。 我们通常建议可以更改这个值或者更改 top_p，但不建议同时对两者进行修改。"
    override val topP: String
        get() = "TopP"
    override val topPDescription: String
        get() = "作为调节采样温度的替代方案，模型会考虑前 top_p 概率的 token 的结果。所以 0.1 就意味着只有包括在最高 10% 概率中的 token 会被考虑。 我们通常建议修改这个值或者更改 temperature，但不建议同时对两者进行修改。"
    override val topK: String
        get() = "TopK"
    override val topKDescription: String
        get() = "更改模型选择输出词元的方式。如果 topK 设为 1，则表示所选 token 是模型词汇表的所有 token 中概率最高的 token；如果 topK 设为 3，则表示系统将从 3 个概率最高的 token 中选择下一个 token（通过温度确定）。系统会根据 topP 进一步过滤词元，并使用温度采样选择最终的词元。"
    override val frequencyPenalty: String
        get() = "频率惩罚"
    override val frequencyPenaltyDescription: String
        get() = "介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其在已有文本中的出现频率受到相应的惩罚，降低模型重复相同内容的可能性。正惩罚会阻止使用在回答中已使用的令牌，从而增加词汇量。负惩罚会鼓励使用在回答中已使用的令牌，从而减少词汇量。"
    override val presencePenalty: String
        get() = "Token 惩罚"
    override val presencePenaltyDescription: String
        get() = "介于 -2.0 和 2.0 之间的数字。如果该值为正，那么新 token 会根据其是否已在已有文本中出现受到相应的惩罚，从而增加模型谈论新主题的可能性。正惩罚会阻止使用已使用的令牌，惩罚强度与令牌的使用次数成正比：令牌使用次数越多，模型就越难再次使用该令牌来增加回答的词汇量。注意：负惩罚会鼓励模型根据令牌的使用次数重复使用令牌。较小的负值会减少回答的词汇量。负值越大，模型就会开始重复一个常用令牌，直到达到 maxOutputTokens 限制。"
    override val importTavernV2Card: String
        get() = "导入酒馆 V2 卡片\n(PNG/JSON)"
    override val errorImportTavernV2CardNoneFile: String
        get() = "请选择一个文件导入!"
    override val errorImportTavernV2CardFileInvalidation: String
        get() = "请选择一个 PNG 或者 JSON 文件导入!"
    override val errorImportTavernV2CardContentInvalidation: String
        get() = "请选择一个合法酒馆 V2 卡片导入!"
    override val errorPromptInfoIsNull: String
        get() = "提示词信息为空!"
}