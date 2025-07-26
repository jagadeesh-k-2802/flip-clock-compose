package com.jagadeesh.flipclock.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jagadeesh.flipclock.domain.model.TimeState
import com.jagadeesh.flipclock.presentation.screen.FlipClockViewModel
import com.jagadeesh.flipclock.ui.theme.BackgroundColor

@Composable
fun FlipClock(
    modifier: Modifier = Modifier,
    viewModel: FlipClockViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val timeState by viewModel.timeState.collectAsState()

    Box(
        modifier = modifier.background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlipClockContent(timeState)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FlipClockContent(timeState)
            }
        }
    }
}

@Composable
private fun FlipClockContent(timeState: TimeState) {
    FlipDigit(
        digit = timeState.hour,
        isFlipping = timeState.isHourFlipping,
    )

    Spacer(modifier = Modifier.width(32.dp))

    FlipDigit(
        digit = timeState.minute,
        isFlipping = timeState.isMinuteFlipping,
    )
}
