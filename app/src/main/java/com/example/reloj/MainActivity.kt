package com.example.reloj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reloj.ui.ClockGalleryScreen
import com.example.reloj.ui.ClockScreen
import com.example.reloj.ui.ClockSettingsScreen
import com.example.reloj.ui.ClockViewModel
import com.example.reloj.ui.Screen
import com.example.reloj.ui.theme.RELOJTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RELOJTheme {
                val clockViewModel: ClockViewModel = viewModel()
                val uiState by clockViewModel.uiState.collectAsState()

                when (uiState.currentScreen) {
                    Screen.Clock -> {
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

                    Screen.Gallery -> {
                        ClockGalleryScreen(
                            onClockSelected = { clockFaceType ->
                                clockViewModel.selectClockFace(clockFaceType)
                            },
                            onBack = {
                                clockViewModel.navigateTo(Screen.Clock)
                            }
                        )
                    }

                    Screen.Settings -> {
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