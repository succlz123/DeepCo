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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_local_dir
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.AppImgButton
import org.succlz123.deepco.app.base.MainTitleLayout
import org.succlz123.deepco.app.i18n.LocaleLanguage
import org.succlz123.deepco.app.i18n.languageToEN
import org.succlz123.deepco.app.i18n.languageToZH
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
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
            Text(text = strings().llm, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val mcpToolsMode = strings().mcpToolsMode
                val kv = remember(settings, mcpToolsMode) {
                    mcpToolsMode.mapIndexed { index: Int, item: String ->
                        Pair(item, MainSettingViewModel.mcpToolModeKeyList[index])
                    }
                }
                val curSelect = remember(settings) {
                    mutableStateOf(MainSettingViewModel.mcpToolModeKeyList.indexOf(settings?.llmToolMode.orEmpty()))
                }
                Text(text = strings().mcpToolExecutionMode, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.width(12.dp))
                kv.forEachIndexed { index, s ->
                    Text(
                        modifier = Modifier.padding(16.dp, 6.dp).noRippleClick {
                            viewModel.change(llmToolMode = s.second)
                        }, text = s.first, style = MaterialTheme.typography.body1,
                        color = if ((index == curSelect.value)) {
                            ColorResource.theme
                        } else {
                            ColorResource.subText
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().language, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val settingLanguageList = strings().settingLanguageList
                val kv = remember(settings, settingLanguageList) {
                    settingLanguageList.mapIndexed { index: Int, item: String ->
                        Pair(item, MainSettingViewModel.languageList[index])
                    }
                }
                val curSelect = remember(settings) {
                    mutableStateOf(MainSettingViewModel.languageList.indexOf(settings?.languageMode.orEmpty()))
                }
                Text(text = strings().settingLanguage, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.width(12.dp))
                kv.forEachIndexed { index, s ->
                    Text(
                        modifier = Modifier.padding(16.dp, 6.dp).noRippleClick {
                            viewModel.change(languageMode = s.second)
                            if (s.second == LocaleLanguage.EN.name) {
                                languageToEN()
                            } else if (s.second == LocaleLanguage.ZH.name) {
                                languageToZH()
                            }
                        }, text = s.first, style = MaterialTheme.typography.body1,
                        color = if ((index == curSelect.value)) {
                            ColorResource.theme
                        } else {
                            ColorResource.subText
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().localConfigFile, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            AppImgButton(drawable = Res.drawable.ic_local_dir, text = strings().openLocalConfigDir, onClick = {
                org.succlz123.lib.setting.openConfigDir(AppBuildConfig.APP)
            })
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().declaration, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().declarationContent, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().github, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            val url = remember {
                AppBuildConfig.gitHubUrl
            }
            Text(
                text = url, modifier = Modifier.onClickUrl(url),
                color = ColorResource.theme,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().deviceInfo, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = """${strings().platform}: ${getPlatformName()} - ${strings().cpuSize}: ${r.availableProcessors()}
${strings().os}: ${props.getProperty("os.name")} - ${props.getProperty("os.arch")} - ${props.getProperty("os.version")}""",
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = strings().version, style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "v${AppBuildConfig.versionName}", style = MaterialTheme.typography.body2)
        }
    }
}