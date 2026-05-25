package com.example.reloj.ui

import androidx.compose.ui.graphics.Color
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

enum class ClockColorPreset(
    val backgroundColor: Color,
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color
) {
    DEFAULT(Color.Transparent, Color(0xFF6750A4), Color(0xFF625b71), Color(0xFF7D5260)),
    AMOLED(Color.Black, Color.White, Color.Gray, Color.White),
    NEON(Color(0xFF0D0D0D), Color(0xFF00FFD1), Color(0xFF00B8D4), Color(0xFF00FFD1)),
    WARM(Color(0xFFFFF7E6), Color(0xFF8B4513), Color(0xFFD2691E), Color(0xFFFF4500)),
    BLUE(Color(0xFFE3F2FD), Color(0xFF1976D2), Color(0xFF42A5F5), Color(0xFF0D47A1))
}

data class ClockStyleSettings(
    val showSeconds: Boolean = true,
    val showDate: Boolean = true,
    val is24HourFormat: Boolean = true,
    val colorPreset: ClockColorPreset = ClockColorPreset.DEFAULT,
    val showAnalogNumbers: Boolean = true,
    val showAnalogMinuteMarks: Boolean = true
)

sealed class Screen {
    object Clock : Screen()
    object Gallery : Screen()
    object Settings : Screen()
}

data class ClockUiState(
    val currentTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val zoneId: ZoneId = ZoneId.systemDefault(),
    val selectedClockFace: ClockFaceType = ClockFaceType.DIGITAL_MINIMAL,
    val currentScreen: Screen = Screen.Clock,
    val styleSettings: ClockStyleSettings = ClockStyleSettings()
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

    fun updateStyleSettings(update: (ClockStyleSettings) -> ClockStyleSettings) {
        _uiState.value = _uiState.value.copy(
            styleSettings = update(_uiState.value.styleSettings)
        )
    }

    // Individual update functions for convenience
    fun setShowSeconds(show: Boolean) = updateStyleSettings { it.copy(showSeconds = show) }
    fun setShowDate(show: Boolean) = updateStyleSettings { it.copy(showDate = show) }
    fun set24HourFormat(is24Hour: Boolean) = updateStyleSettings { it.copy(is24HourFormat = is24Hour) }
    fun setColorPreset(preset: ClockColorPreset) = updateStyleSettings { it.copy(colorPreset = preset) }
    fun setShowAnalogNumbers(show: Boolean) = updateStyleSettings { it.copy(showAnalogNumbers = show) }
    fun setShowAnalogMinuteMarks(show: Boolean) = updateStyleSettings { it.copy(showAnalogMinuteMarks = show) }

    fun changeTimeZone(zoneId: ZoneId) {
        _uiState.value = _uiState.value.copy(
            zoneId = zoneId,
            currentTime = Instant.now().atZone(zoneId)
        )
    }
}
