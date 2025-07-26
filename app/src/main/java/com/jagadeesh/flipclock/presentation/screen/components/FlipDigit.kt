package com.jagadeesh.flipclock.presentation.screen.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.jagadeesh.flipclock.ui.theme.DigitBoxColor
import com.jagadeesh.flipclock.ui.theme.DigitLineColor
import com.jagadeesh.flipclock.ui.theme.FlipDigitStyle

@Composable
fun FlipDigit(
    modifier: Modifier = Modifier,
    digit: String,
    isFlipping: Boolean,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val previousDigit = remember { mutableStateOf(digit) }

    val rotation by animateFloatAsState(
        targetValue = if (isFlipping) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "flip"
    )

    if (!isFlipping) {
        previousDigit.value = digit
    }

    val boxWidth = if (isLandscape) {
        screenHeight * 0.7f
    } else {
        screenWidth * 0.7f
    }

    val childBoxHeight = if (isLandscape) {
        (configuration.screenWidthDp / 2).dp
    } else {
        (configuration.screenHeightDp / 2).dp
    }

    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .width(boxWidth)
            .aspectRatio(1f)
            .background(DigitBoxColor)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = DigitLineColor,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { childBoxHeight })
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(DigitBoxColor)
        ) {
            Text(
                text = digit,
                style = FlipDigitStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { childBoxHeight })
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    rotationX = rotation
                    cameraDistance = 12f * density.density
                    transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(DigitBoxColor)
        ) {
            Text(
                text = if (rotation < 90f) previousDigit.value else digit,
                style = FlipDigitStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .graphicsLayer {
                        rotationX = if (rotation < 90f) 0f else 180f
                    }
            )
        }
    }
}
