package com.example.reloj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reloj.ui.ClockStyleSettings
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FlipClockFace(
    time: ZonedDateTime,
    settings: ClockStyleSettings,
    modifier: Modifier = Modifier
) {
    val preset = settings.colorPreset
    val cardColor = if (preset.primaryColor == Color.Transparent || preset.primaryColor == Color.White) {
        Color(0xFF333333)
    } else {
        preset.primaryColor
    }
    val textColor = if (preset.primaryColor == Color.Transparent || preset.primaryColor == Color.White) {
        Color.White
    } else {
        preset.backgroundColor.takeIf { it != Color.Transparent } ?: Color.White
    }

    val hour = if (settings.is24HourFormat) {
        time.format(DateTimeFormatter.ofPattern("HH"))
    } else {
        time.format(DateTimeFormatter.ofPattern("hh"))
    }
    val minute = time.format(DateTimeFormatter.ofPattern("mm"))
    val second = time.format(DateTimeFormatter.ofPattern("ss"))
    val amPm = if (!settings.is24HourFormat) time.format(DateTimeFormatter.ofPattern("a")) else ""

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FlipCard(value = hour, cardColor = cardColor, textColor = textColor)
            Text(
                text = ":",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = cardColor
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            FlipCard(value = minute, cardColor = cardColor, textColor = textColor)
            
            if (settings.showSeconds) {
                Text(
                    text = ":",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = cardColor
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                FlipCard(value = second, cardColor = cardColor, textColor = textColor)
            }
        }

        if (!settings.is24HourFormat) {
            Text(
                text = amPm,
                style = MaterialTheme.typography.headlineMedium,
                color = cardColor,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (settings.showDate) {
            Text(
                text = time.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")),
                style = MaterialTheme.typography.bodyLarge,
                color = cardColor.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

@Composable
fun FlipCard(
    value: String,
    cardColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 140.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Black,
                        color = textColor
                    )
                )
            }
            // Linea horizontal para simular el corte del panel
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }
    }
}
