package com.example.reloj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DigitalNeonClockFace(
    time: ZonedDateTime,
    modifier: Modifier = Modifier
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val dateFormatter = DateTimeFormatter.ofPattern("dd / MM / yyyy")
    
    val neonColor = Color(0xFF00FFD1) // Cian Neón

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
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
        
        Text(
            text = time.format(dateFormatter),
            style = MaterialTheme.typography.headlineSmall,
            color = neonColor.copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
