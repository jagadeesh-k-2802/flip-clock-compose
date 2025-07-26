package com.jagadeesh.flipclock.domain.usecase

import com.jagadeesh.flipclock.domain.model.TimeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTimeStateUseCase @Inject constructor() {

    companion object {
        private const val ANIMATION_DURATION = 400L
    }

    private val timeFormatter = DateTimeFormatter.ofPattern("HH")
    private val minuteFormatter = DateTimeFormatter.ofPattern("mm")

    operator fun invoke(): Flow<TimeState> = flow {
        // Emit initial state
        val initialTime = LocalTime.now()
        val initialHour = initialTime.format(timeFormatter)
        val initialMinute = initialTime.format(minuteFormatter)
        emit(TimeState(initialHour, initialMinute))

        var previousHour = initialHour
        var previousMinute = initialMinute

        while (true) {
            val currentTime = LocalTime.now()
            val newHour = currentTime.format(timeFormatter)
            val newMinute = currentTime.format(minuteFormatter)

            when {
                newHour != previousHour -> {
                    emit(TimeState(newHour, previousMinute, isHourFlipping = true))
                    delay(ANIMATION_DURATION)
                    previousHour = newHour
                    emit(TimeState(newHour, previousMinute))
                }

                newMinute != previousMinute -> {
                    emit(TimeState(previousHour, newMinute, isMinuteFlipping = true))
                    delay(ANIMATION_DURATION)
                    previousMinute = newMinute
                    emit(TimeState(previousHour, newMinute))
                }
            }

            // Calculate delay until next minute
            val now = LocalTime.now()
            val secondsUntilNextMinute = 60 - now.second
            val delayMillis = (secondsUntilNextMinute * 1000L) - (now.nano / 1_000_000L)
            delay(delayMillis.coerceAtLeast(0))
        }
    }
}
