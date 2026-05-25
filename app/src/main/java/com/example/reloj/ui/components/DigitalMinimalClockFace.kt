package com.example.reloj.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.reloj.ui.ClockStyleSettings
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DigitalMinimalClockFace(
    time: ZonedDateTime,
    settings: ClockStyleSettings,
    modifier: Modifier = Modifier
) {
    val timePattern = when {
        settings.is24HourFormat && settings.showSeconds -> "HH:mm:ss"
        settings.is24HourFormat && !settings.showSeconds -> "HH:mm"
        !settings.is24HourFormat && settings.showSeconds -> "hh:mm:ss a"
        else -> "hh:mm a"
    }

    val timeFormatter = DateTimeFormatter.ofPattern(timePattern, Locale("es", "MX"))
    val dateFormatter = DateTimeFormatter.ofPattern(
        "EEEE, d 'de' MMMM 'de' yyyy",
        Locale("es", "MX")
    )

    val preset = settings.colorPreset
    val primaryColor = if (preset.primaryColor == Color.Transparent) {
        MaterialTheme.colorScheme.primary
    } else {
        preset.primaryColor
    }

    val secondaryColor = if (preset.secondaryColor == Color.Transparent) {
        MaterialTheme.colorScheme.secondary
    } else {
        preset.secondaryColor
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = time.format(timeFormatter),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold
            ),
            color = primaryColor
        )

        if (settings.showDate) {
            Text(
                text = time.format(dateFormatter).replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyLarge,
                color = secondaryColor
            )
        }
    }
}