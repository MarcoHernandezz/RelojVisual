package com.example.reloj.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reloj.ui.components.AnalogClassicClockFace
import com.example.reloj.ui.components.DigitalMinimalClockFace
import com.example.reloj.ui.components.DigitalNeonClockFace

@Composable
fun ClockScreen(
    viewModel: ClockViewModel,
    onOpenGallery: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val settings = uiState.styleSettings

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = onOpenGallery,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Galería"
                    )
                }

                FloatingActionButton(
                    onClick = onOpenSettings
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ajustes"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(settings.colorPreset.backgroundColor)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    when (uiState.selectedClockFace) {
                        ClockFaceType.DIGITAL_MINIMAL -> {
                            DigitalMinimalClockFace(
                                time = uiState.currentTime,
                                settings = settings
                            )
                        }

                        ClockFaceType.DIGITAL_NEON -> {
                            DigitalNeonClockFace(
                                time = uiState.currentTime,
                                settings = settings
                            )
                        }

                        ClockFaceType.ANALOG_CLASSIC -> {
                            AnalogClassicClockFace(
                                time = uiState.currentTime,
                                settings = settings
                            )
                        }

                        else -> {
                            DigitalMinimalClockFace(
                                time = uiState.currentTime,
                                settings = settings
                            )
                        }
                    }
                }

                Text(
                    text = if (settings.useDeviceTimeZone) {
                        "Zona del dispositivo"
                    } else {
                        uiState.zoneId.id
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}