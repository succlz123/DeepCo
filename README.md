# Deep-Co

<p align="center">
<kbd>
  <img alt="icon" src="desktopApp/icon.png" width="128" height="128">
</kbd>
  <br>
  <br>
  <img alt="windows" src="http://img.shields.io/badge/-Windows-477FE4.svg?style=flat">
  <img alt="macos" src="http://img.shields.io/badge/-MacOS-FCF0E7.svg?style=flat">
  <img alt="linux" src="http://img.shields.io/badge/-Linux-5D1A42.svg?style=flat">
  <br>
  <img alt="android" src="http://img.shields.io/badge/-Android-00FF00.svg?style=flat">
  <img alt="iOS" src="http://img.shields.io/badge/-iOS-000000.svg?style=flat">
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


## Release

[v1.0.5](https://github.com/succlz123/DeepCo/releases)


## Feature

- [x] Desktop Platform Support(Windows/MacOS/Linux)
- [ ] Mobile Platform Support(Android/iOS)
- [x] Chat(Stream&Complete) / Chat History
- [ ] Chat Messages Export / Chat Translate Server
- [x] Prompt Management / User Define
- [x] SillyTavern Character Adaptation(PNG&JSON)
- [x] DeepSeek LLM / Grok LLM / Google Gemini LLM
- [ ] Claude LLM / OpenAI LLM / OLLama LLM
- [ ] Online API polling
- [x] MCP Support
- [ ] MCP Server Market
- [ ] RAG
- [x] TTS(Edge API)
- [ ] Dark/Light Theme
- [x] i18n(Chinese/English) / App Theme

### Chat With LLMs

![1](screenshots/1.jpg)

### Config Your LLMs API Key

![2](screenshots/2.jpg)

### Prompt Management

![4](screenshots/4.jpg)

### Chat With Tavern Character

![6](screenshots/6.jpg)

### User Management

![5](screenshots/5.jpg)

### Config MCP Servers

![3](screenshots/3.jpg)

### Setting

![7](screenshots/7.jpg)


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
./gradlew :desktopApp:run
```

### Building desktop distribution

```
./gradlew :desktop:packageDistributionForCurrentOS
# outputs are written to desktopApp/build/compose/binaries
```

### Run Android via Gradle

```
./gradlew :androidApp:installDebug
```

### Building Android distribution

```
./gradlew clean :androidApp:assembleRelease
# outputs are written to androidApp/build/outputs/apk/release
```

## Thanks

- [Compose-Multiplatform](https://github.com/JetBrains/compose-multiplatform)
- [MCP-Kotlin-SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
- [DeepSeek](https://api-docs.deepseek.com/zh-cn/)
- [HyperChat](https://github.com/BigSweetPotatoStudio/HyperChat)
- [SillyTavern](https://github.com/SillyTavern/SillyTavern)