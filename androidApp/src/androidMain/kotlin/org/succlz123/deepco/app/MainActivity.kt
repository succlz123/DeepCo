package org.succlz123.deepco.app

import MainAndroidActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.succlz123.deepco.app.theme.AppTheme
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.screen.ScreenContainer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            ScreenContainer(this, this, this, this) {
                AppTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ColorResource.background)
                            .padding(top = WindowInsets.statusBars.getTop(LocalDensity.current).dp)
                    ) {
                        MainAndroidActivity()
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}