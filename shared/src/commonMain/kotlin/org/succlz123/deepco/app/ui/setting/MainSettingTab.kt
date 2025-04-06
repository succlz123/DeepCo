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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.russhwolf.settings.set
import org.succlz123.deepco.app.base.AppHorizontalDivider
import org.succlz123.deepco.app.base.MainRightTitleLayout
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.deepco.app.ui.setting.MainSettingViewModel.Companion.KEY_LLM_TOOL_MODE
import org.succlz123.lib.click.noRippleClickable
import org.succlz123.lib.common.getPlatformName
import org.succlz123.lib.screen.viewmodel.viewModel

@Composable
fun MainSettingTab(modifier: Modifier = Modifier) {
    val viewModel = viewModel { MainSettingViewModel() }
    MainRightTitleLayout(modifier.verticalScroll(rememberScrollState()), text = "Setting") {
        val r = remember { Runtime.getRuntime() }
        val props = remember { System.getProperties() }
        val map = remember { System.getenv() }

        Column(
            modifier = Modifier.padding(26.dp, 26.dp, 26.dp, 26.dp).fillMaxSize()
        ) {
            Text(text = "LLM", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val mode = viewModel.llmToolMode.collectAsState()
                Text(text = "Tool execution mode:", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.width(12.dp))
                MainSettingViewModel.llmToolModeList.forEachIndexed { index, s ->
                    Text(
                        modifier = Modifier.padding(16.dp, 6.dp).noRippleClickable {
                            viewModel.llmToolMode.value = s
                            viewModel.setting[KEY_LLM_TOOL_MODE] = s
                        }, text = s, style = MaterialTheme.typography.body1,
                        color = if ((index == MainSettingViewModel.llmToolModeList.indexOf(mode.value))) {
                            ColorResource.theme
                        } else {
                            ColorResource.subText
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Declaration", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "As an LLM Client, all internal resources come from the Internet. This application is only for sharing and will not save any user-related information at present.",
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Github", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "https://github.com/succlz123/DeepCo", style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Device Info", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = """Platform: ${getPlatformName()} - CPU Size: ${r.availableProcessors()}

OS: ${props.getProperty("os.name")} - ${props.getProperty("os.arch")} - ${props.getProperty("os.version")}
""", style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(12.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Version", style = MaterialTheme.typography.h3)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "v0.0.1", style = MaterialTheme.typography.body2)
        }
    }
}