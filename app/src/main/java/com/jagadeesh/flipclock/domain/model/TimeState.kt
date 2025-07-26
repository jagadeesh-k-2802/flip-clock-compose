package com.jagadeesh.flipclock.domain.model

data class TimeState(
    val hour: String,
    val minute: String,
    val isHourFlipping: Boolean = false,
    val isMinuteFlipping: Boolean = false
)
