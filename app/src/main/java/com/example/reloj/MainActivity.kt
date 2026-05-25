package com.example.reloj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.reloj.ui.ClockScreen
import com.example.reloj.ui.theme.RELOJTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RELOJTheme {
                ClockScreen()
            }
        }
    }
}
