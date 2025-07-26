package com.jagadeesh.flipclock.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.jagadeesh.flipclock.presentation.screen.components.FlipClock
import com.jagadeesh.flipclock.ui.theme.FlipClockTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setupUI()
    }

    private fun setupWindow() {
        enableEdgeToEdge()
        setupRefreshRate()
        setupFullscreen()
        setupKeepScreenOn()
    }

    private fun setupRefreshRate() {
        window.attributes = window.attributes.apply {
            preferredRefreshRate = 1f
            preferredDisplayModeId = 0
        }
    }

    private fun setupFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            isAppearanceLightStatusBars = true
        }
    }

    private fun setupKeepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun setupUI() {
        setContent {
            FlipClockTheme {
                FlipClock()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupRefreshRate()
    }
}
