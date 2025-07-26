package com.jagadeesh.flipclock.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadeesh.flipclock.domain.model.TimeState
import com.jagadeesh.flipclock.domain.usecase.GetTimeStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FlipClockViewModel @Inject constructor(
    private val getTimeStateUseCase: GetTimeStateUseCase
) : ViewModel() {

    private val _timeState = MutableStateFlow(
        TimeState(
            hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH")),
            minute = LocalTime.now().format(DateTimeFormatter.ofPattern("mm"))
        )
    )
    val timeState: StateFlow<TimeState> = _timeState.asStateFlow()

    init {
        viewModelScope.launch {
            getTimeStateUseCase().collect { newState ->
                _timeState.value = newState
            }
        }
    }
}