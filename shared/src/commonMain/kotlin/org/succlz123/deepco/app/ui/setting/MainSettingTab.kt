package org.succlz123.deepco.app.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_local_dir
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppImgButton
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.i18n.LocaleLanguage
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.mcp.biz.ToolConfig
import org.succlz123.deepco.app.theme.AppDarkThemeConfig
import org.succlz123.deepco.app.theme.AppThemeConfig
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.click.onClickUrl
import org.succlz123.lib.common.getPlatformName
import org.succlz123.lib.screen.viewmodel.viewModel


@Composable
fun MainSettingTab(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MainSettingViewModel() }
    MainTitleLayout(modifier.verticalScroll(rememberScrollState()), text = strings().setting) {
        val r = remember { Runtime.getRuntime() }
        val props = remember { System.getProperties() }
        val map = remember { System.getenv() }
        val settings = viewModel.settingLocal.collectAsState().value
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Text(text = strings().llm, style = MaterialTheme.typography.headlineMedium)
            SettingSelection(settings.llmToolMode, strings().mcpToolExecutionMode, strings().mcpToolsExecutionModeList, ToolConfig.names()) {
                viewModel.change(llmToolMode = it)
            }
            SettingDivider()
            Text(text = strings().client, style = MaterialTheme.typography.headlineMedium)
            SettingSelection(settings.languageMode, strings().settingLanguage, strings().settingLanguageList, LocaleLanguage.names()) {
                viewModel.change(languageMode = it)
            }
            SettingSelection(settings.appThemeConfig, strings().settingAppTheme, strings().settingAppThemeList, AppThemeConfig.names()) {
                viewModel.change(appTheme = it)
            }
            SettingSelection(settings.appDarkThemeConfig, strings().settingAppDarkTheme, strings().settingAppDarkThemeList, AppDarkThemeConfig.names()) {
                viewModel.change(appThemeDark = it)
            }
            SettingDivider()
            Text(text = strings().localConfigFile, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            AppImgButton(drawable = Res.drawable.ic_local_dir, text = strings().openLocalConfigDir, onClick = {
                org.succlz123.lib.setting.openConfigDir(AppBuildConfig.APP)
            })
            SettingDivider()
            Text(text = strings().declaration, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().declarationContent, style = MaterialTheme.typography.bodyMedium)
            SettingDivider()
            Text(text = strings().github, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            val url = remember {
                AppBuildConfig.gitHubUrl
            }
            Text(
                text = url, modifier = Modifier.onClickUrl(url),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            SettingDivider()
            Text(text = strings().deviceInfo, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = """${strings().platform}: ${getPlatformName()} - ${strings().cpuSize}: ${r.availableProcessors()}
${strings().os}: ${props.getProperty("os.name")} - ${props.getProperty("os.arch")} - ${props.getProperty("os.version")}""",
                style = MaterialTheme.typography.bodyMedium
            )
            SettingDivider()
            Text(text = strings().version, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "v${AppBuildConfig.versionName}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SettingDivider() {
    Spacer(modifier = Modifier.height(12.dp))
    AppHorizontalDivider()
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun SettingSelection(mode: String, title: String, selectStringList: List<String>, enumList: List<String>, change: (String) -> Unit) {
    Spacer(modifier = Modifier.height(12.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        val kv = remember(mode, selectStringList) {
            selectStringList.mapIndexed { index: Int, item: String ->
                Pair(item, enumList[index])
            }
        }
        val curSelect = remember(mode) {
            mutableStateOf(enumList.indexOf(mode))
        }
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.width(12.dp))
        kv.forEachIndexed { index, s ->
            Text(
                modifier = Modifier.padding(16.dp, 6.dp).noRippleClick {
                    change(s.second)
                }, text = s.first, style = MaterialTheme.typography.bodyMedium,
                color = if ((index == curSelect.value)) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHighest
                }
            )
        }
    }
}