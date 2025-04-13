# Deep-Co

<p align="center">
<kbd>
  <img alt="icon" src="desktopApp/icon.png" width="128" height="128">
</kbd>
  <br>
  <br>
  <img alt="windows" src="http://img.shields.io/badge/-windows-4D76CD.svg?style=flat">
  <img alt="macos" src="http://img.shields.io/badge/-macos-111111.svg?style=flat">
  <img alt="linux" src="http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat">
  <br>
  <img alt="kotlin" src="https://img.shields.io/badge/kotlin-2.1.20-blue.svg?logo=kotlin">
  <img alt="compose" src="https://img.shields.io/badge/compose-1.7.3-blue?logoColor=f5f5f5">
  <br>
  <img alt="stars" src="https://img.shields.io/github/stars/succlz123/DeepCo?color=pink&style=plastic">
  <img alt="gpl" src="https://img.shields.io/badge/license-GPL--3.0-orange">
  <img alt="release" src="https://img.shields.io/github/v/release/succlz123/DeepCo?color=blueviolet&display_name=tag&include_prereleases&label=Release">
</p>


A Chat Client for LLMs, written in Compose Multiplatform. Target supports API providers such as OpenRouter, Anthropic, Grok, OpenAI, DeepSeek,
Coze, Dify, Google Gemini, etc. You can also configure any OpenAI-compatible API or use native models via LM Studio/Ollama.


## Feature

- [x] Chat(Stream&Complete)
- [x] Chat History
- [ ] Chat Messages Export
- [ ] Chat Translate Server
- [x] Prompt Management
- [x] User Define
- [x] SillyTavern Character Adaptation(PNG&JSON)
- [x] DeepSeek LLM
- [x] Grok LLM
- [x] Google Gemini LLM
- [ ] Claude LLM
- [ ] OpenAI LLM
- [ ] OLLama LLM
- [ ] Online API polling
- [x] MCP Support
- [ ] MCP Server Market
- [ ] RAG
- [x] TTS(Edge API)
- [ ] Dark/Light Theme
- [ ] i18n(Chinese/English)

### Chat With LLMs

![1](screenshots/1.jpg)

### Config Your LLMs API Key

![2](screenshots/2.jpg)

### Prompt Management

![4](screenshots/4.png)

### Chat With Tavern Character

![6](screenshots/6.jpg)

### User Management

![5](screenshots/5.png)

### Config MCP Servers

![3](screenshots/3.jpg)

## Model Context Protocol (MCP) ENV

### MacOS

``` 
brew install uv
brew install node
```

### windows

```
winget install --id=astral-sh.uv  -e
winget install OpenJS.NodeJS.LTS
```

## Build

### Run desktop via Gradle

```
./gradlew desktopApp:run
```

### Building desktop distribution

```
./gradlew :desktop:packageDistributionForCurrentOS
# outputs are written to desktopApp/build/compose/binaries
```

## Thanks

- [Compose-Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [MCP-Kotlin-SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
- [DeepSeek](https://api-docs.deepseek.com/zh-cn/)
- [HyperChat](https://github.com/BigSweetPotatoStudio/HyperChat)
- [SillyTavern](https://github.com/SillyTavern/SillyTavern)