package com.example.reloj.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reloj.domain.SystemTimeProvider
import com.example.reloj.domain.TimeProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

sealed class Screen {
    object Clock : Screen()
    object Gallery : Screen()
}

data class ClockUiState(
    val currentTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val zoneId: ZoneId = ZoneId.systemDefault(),
    val selectedClockFace: ClockFaceType = ClockFaceType.DIGITAL_MINIMAL,
    val currentScreen: Screen = Screen.Clock
)

class ClockViewModel(
    private val timeProvider: TimeProvider = SystemTimeProvider()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClockUiState())
    val uiState: StateFlow<ClockUiState> = _uiState.asStateFlow()

    init {
        startClock()
    }

    private fun startClock() {
        viewModelScope.launch {
            timeProvider.getTimeTicks().collect { instant ->
                updateTime(instant)
            }
        }
    }

    private fun updateTime(instant: Instant) {
        _uiState.value = _uiState.value.copy(
            currentTime = instant.atZone(_uiState.value.zoneId)
        )
    }

    fun selectClockFace(clockFaceType: ClockFaceType) {
        if (clockFaceType.isAvailable) {
            _uiState.value = _uiState.value.copy(
                selectedClockFace = clockFaceType,
                currentScreen = Screen.Clock
            )
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.value = _uiState.value.copy(currentScreen = screen)
    }

    fun changeTimeZone(zoneId: ZoneId) {
        _uiState.value = _uiState.value.copy(
            zoneId = zoneId,
            currentTime = Instant.now().atZone(zoneId)
        )
    }
}
