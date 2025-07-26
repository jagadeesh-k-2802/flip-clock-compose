package com.jagadeesh.flipclock.ui.theme

import android.app.Activity
import android.service.dreams.DreamService
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DigitTextColor,
    secondary = DigitLineColor,
    background = BackgroundColor,
    surface = DigitBoxColor,
    onPrimary = BackgroundColor,
    onSecondary = BackgroundColor,
    onBackground = DigitTextColor,
    onSurface = DigitTextColor
)

@Composable
fun FlipClockTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            when (val context = view.context) {
                is Activity -> {
                    context.window.statusBarColor = colorScheme.background.toArgb()
                    WindowCompat.getInsetsController(context.window, view).isAppearanceLightStatusBars = true
                }

                is DreamService -> {
                    // DreamService already handles its own window decorations
                    // No need to modify status bar or system bars
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
