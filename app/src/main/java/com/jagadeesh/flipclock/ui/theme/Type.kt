package com.jagadeesh.flipclock.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jagadeesh.flipclock.R

val FlipClockFont = FontFamily(
    Font(R.font.flipclock, FontWeight.Normal)
)

val FlipDigitStyle = TextStyle(
    fontSize = 160.sp,
    fontFamily = FlipClockFont,
    color = DigitTextColor,
    textAlign = TextAlign.Center
)
