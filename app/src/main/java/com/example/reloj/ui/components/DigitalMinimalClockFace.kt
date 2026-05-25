package com.example.reloj.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DigitalMinimalClockFace(
    time: ZonedDateTime,
    modifier: Modifier = Modifier
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")

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
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = time.format(dateFormatter),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
