package com.example.reloj.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockSettingsScreen(
    viewModel: ClockViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val settings = uiState.styleSettings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalizar Reloj") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Configuración General", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            
            SettingSwitch(
                label = "Mostrar Segundos",
                checked = settings.showSeconds,
                onCheckedChange = { viewModel.setShowSeconds(it) }
            )

            SettingSwitch(
                label = "Mostrar Fecha",
                checked = settings.showDate,
                onCheckedChange = { viewModel.setShowDate(it) }
            )

            SettingSwitch(
                label = "Formato 24 Horas",
                checked = settings.is24HourFormat,
                onCheckedChange = { viewModel.set24HourFormat(it) }
            )

            Divider()

            Text("Configuración Analógica", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

            SettingSwitch(
                label = "Mostrar Números",
                checked = settings.showAnalogNumbers,
                onCheckedChange = { viewModel.setShowAnalogNumbers(it) }
            )

            SettingSwitch(
                label = "Mostrar Marcas de Minutos",
                checked = settings.showAnalogMinuteMarks,
                onCheckedChange = { viewModel.setShowAnalogMinuteMarks(it) }
            )

            Divider()

            Text("Preajuste de Colores", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ClockColorPreset.entries.forEach { preset ->
                    ColorPresetButton(
                        preset = preset,
                        isSelected = settings.colorPreset == preset,
                        onClick = { viewModel.setColorPreset(preset) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar y Volver")
            }
        }
    }
}

@Composable
fun SettingSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun ColorPresetButton(
    preset: ClockColorPreset,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (preset.backgroundColor == Color.Transparent) MaterialTheme.colorScheme.surfaceVariant else preset.backgroundColor)
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(preset.primaryColor),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
    }
}
