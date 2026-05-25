package com.example.reloj.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reloj.ui.components.DigitalMinimalClockFace

@Composable
fun ClockScreen(
    viewModel: ClockViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            DigitalMinimalClockFace(
                time = uiState.currentTime
            )
        }
    }
}
