package com.example.reloj.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reloj.ui.components.AnalogClassicClockFace
import com.example.reloj.ui.components.DigitalMinimalClockFace
import com.example.reloj.ui.components.DigitalNeonClockFace

@Composable
fun ClockScreen(
    viewModel: ClockViewModel,
    onOpenGallery: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenGallery) {
                Icon(Icons.Default.Settings, contentDescription = "Cambiar Reloj")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.selectedClockFace) {
                ClockFaceType.DIGITAL_MINIMAL -> {
                    DigitalMinimalClockFace(time = uiState.currentTime)
                }
                ClockFaceType.DIGITAL_NEON -> {
                    DigitalNeonClockFace(time = uiState.currentTime)
                }
                ClockFaceType.ANALOG_CLASSIC -> {
                    AnalogClassicClockFace(time = uiState.currentTime)
                }
                else -> {
                    // Fallback para tipos no implementados
                    DigitalMinimalClockFace(time = uiState.currentTime)
                }
            }
        }
    }
}
