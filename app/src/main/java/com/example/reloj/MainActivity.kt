package com.example.reloj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reloj.data.ClockSettingsRepository
import com.example.reloj.ui.*
import com.example.reloj.ui.theme.RELOJTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Instanciar el repositorio aquí para pasarlo al ViewModel
        val repository = ClockSettingsRepository(applicationContext)

        setContent {
            RELOJTheme {
                // 2. Usar una Factory para inyectar el repositorio en el ClockViewModel
                val clockViewModel: ClockViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ClockViewModel(repository) as T
                        }
                    }
                )
                
                val uiState by clockViewModel.uiState.collectAsState()

                when (uiState.currentScreen) {
                    is Screen.Clock -> {
                        ClockScreen(
                            viewModel = clockViewModel,
                            onOpenGallery = {
                                clockViewModel.navigateTo(Screen.Gallery)
                            },
                            onOpenSettings = {
                                clockViewModel.navigateTo(Screen.Settings)
                            }
                        )
                    }

                    is Screen.Gallery -> {
                        ClockGalleryScreen(
                            onClockSelected = { clockFaceType ->
                                clockViewModel.selectClockFace(clockFaceType)
                            },
                            onBack = {
                                clockViewModel.navigateTo(Screen.Clock)
                            }
                        )
                    }

                    is Screen.Settings -> {
                        ClockSettingsScreen(
                            viewModel = clockViewModel,
                            onBack = {
                                clockViewModel.navigateTo(Screen.Clock)
                            }
                        )
                    }
                }
            }
        }
    }
}
