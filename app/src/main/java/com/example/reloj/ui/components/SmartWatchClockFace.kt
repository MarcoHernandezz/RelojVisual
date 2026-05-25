package com.example.reloj.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun SmartWatchClockFace(
    time: ZonedDateTime,
    settings: ClockStyleSettings,
    zoneLabel: String,
    modifier: Modifier = Modifier
) {
    val preset = settings.colorPreset
    val primaryColor = if (preset.primaryColor == Color.Transparent) MaterialTheme.colorScheme.primary else preset.primaryColor
    val onSurface = MaterialTheme.colorScheme.onSurface
    
    val timePattern = if (settings.is24HourFormat) {
        if (settings.showSeconds) "HH:mm:ss" else "HH:mm"
    } else {
        if (settings.showSeconds) "hh:mm:ss a" else "hh:mm a"
    }
    
    val timeString = time.format(DateTimeFormatter.ofPattern(timePattern))
    val dateString = time.format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy"))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Smart Mode Header
        Text(
            text = "SMART MODE",
            style = MaterialTheme.typography.labelSmall,
            color = primaryColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Main Time Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = onSurface.copy(alpha = 0.05f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeString,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 52.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = primaryColor
                    )
                )
                
                if (settings.showDate) {
                    Text(
                        text = dateString.uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        // Info Grid
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Zone Card
            SmartInfoCard(
                label = "ZONE",
                value = zoneLabel,
                primaryColor = primaryColor,
                modifier = Modifier.weight(1f)
            )
            
            // Visual Clock Card
            SmartInfoCard(
                label = "APP",
                value = "VISUAL CLOCK",
                primaryColor = primaryColor,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Desktop Mode Indicator
            SmartInfoCard(
                label = "DESKTOP",
                value = if (settings.keepScreenOn) "ACTIVE" else "OFF",
                primaryColor = primaryColor,
                modifier = Modifier.weight(1f)
            )
            
            // Format Indicator
            SmartInfoCard(
                label = "FORMAT",
                value = if (settings.is24HourFormat) "24H" else "12H",
                primaryColor = primaryColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SmartInfoCard(
    label: String,
    value: String,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = primaryColor.copy(alpha = 0.7f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}
