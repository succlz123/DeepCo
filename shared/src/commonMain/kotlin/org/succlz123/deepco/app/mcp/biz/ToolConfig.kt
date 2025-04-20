package org.succlz123.deepco.app.mcp.biz

enum class ToolConfig {
    Automatic, Manual;

    companion object {
        fun names(): List<String> = entries.map { it.name }
    }
}