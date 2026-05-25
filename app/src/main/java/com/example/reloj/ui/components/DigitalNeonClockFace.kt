package com.example.reloj.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reloj.ui.ClockStyleSettings
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DigitalNeonClockFace(
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
    val dateFormatter = DateTimeFormatter.ofPattern("dd / MM / yyyy", Locale("es", "MX"))

    val preset = settings.colorPreset

    val neonColor = if (preset.primaryColor == Color.Transparent) {
        Color(0xFF00FFD1)
    } else {
        preset.primaryColor
    }

    val secondaryColor = if (preset.secondaryColor == Color.Transparent) {
        neonColor.copy(alpha = 0.8f)
    } else {
        preset.secondaryColor
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Estilo Neón",
            style = MaterialTheme.typography.labelLarge,
            color = neonColor.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = time.format(timeFormatter),
            style = TextStyle(
                fontSize = 85.sp,
                fontWeight = FontWeight.ExtraBold,
                color = neonColor,
                shadow = Shadow(
                    color = neonColor,
                    blurRadius = 15f
                )
            )
        )

        if (settings.showDate) {
            Text(
                text = time.format(dateFormatter),
                style = MaterialTheme.typography.headlineSmall,
                color = secondaryColor,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}