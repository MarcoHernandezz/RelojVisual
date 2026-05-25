package com.example.reloj.ui

enum class ClockFaceType(
    val displayName: String,
    val description: String,
    val isAvailable: Boolean
) {
    DIGITAL_MINIMAL("Digital Minimal", "Un diseño limpio y moderno.", true),
    DIGITAL_NEON("Digital Neón", "Estilo vibrante con efecto neón.", true),
    ANALOG_CLASSIC("Analógico Clásico", "Reloj de agujas tradicional.", false),
    ANALOG_ROMAN("Analógico Romano", "Elegancia clásica con números romanos.", false),
    FLIP_CLOCK("Flip Clock", "Estilo retro de paneles mecánicos.", false),
    SMART_WATCH("Smart Watch", "Interfaz moderna tipo reloj inteligente.", false),
    DIVE_WATCH("Dive Watch", "Inspirado en relojes de buceo.", false),
    MECHANICAL("Mecánico", "Vista detallada del mecanismo interno.", false)
}
