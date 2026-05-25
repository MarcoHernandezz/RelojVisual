package com.example.reloj.ui

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.reloj.ui.components.AnalogClassicClockFace
import com.example.reloj.ui.components.AnalogRomanClockFace
import com.example.reloj.ui.components.DigitalMinimalClockFace
import com.example.reloj.ui.components.DigitalNeonClockFace
import com.example.reloj.ui.components.FlipClockFace

@Composable
fun ClockScreen(
    viewModel: ClockViewModel,
    onOpenGallery: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val settings = uiState.styleSettings
    val context = LocalContext.current
    val view = LocalView.current

    // Requisito 8: Mantener pantalla encendida
    DisposableEffect(settings.keepScreenOn) {
        val activity = context as? Activity
        val window = activity?.window
        if (settings.keepScreenOn) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    // Requisito 9: Modo pantalla completa
    SideEffect {
        val window = (context as? Activity)?.window
        if (window != null) {
            val controller = WindowCompat.getInsetsController(window, view)
            if (settings.fullScreenMode) {
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                controller.show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            // Requisito 7: Ocultar botones flotantes si showControls es false
            if (settings.showControls) {
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(settings.colorPreset.backgroundColor)
                // Requisito 7 y 8: Tocar el fondo para alternar controles
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    viewModel.toggleControls()
                }
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

                        ClockFaceType.ANALOG_ROMAN -> {
                            AnalogRomanClockFace(
                                time = uiState.currentTime,
                                settings = settings
                            )
                        }

                        ClockFaceType.FLIP_CLOCK -> {
                            FlipClockFace(
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

                if (settings.showControls) {
                    Text(
                        text = if (settings.useDeviceTimeZone) {
                            "Zona del dispositivo"
                        } else {
                            uiState.zoneId.id
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = if (uiState.selectedClockFace == ClockFaceType.DIGITAL_NEON) 
                            Color(0xFF00FFD1).copy(alpha = 0.6f)
                        else 
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}
