package com.example.reloj.ui

enum class ClockFaceType(
    val displayName: String,
    val description: String,
    val isAvailable: Boolean
) {
    DIGITAL_MINIMAL("Digital minimalista", "Diseño limpio y moderno enfocado en la simplicidad.", true),
    DIGITAL_NEON("Digital neón", "Efecto vibrante con colores neón sobre fondo oscuro.", true),
    ANALOG_CLASSIC("Analógico clásico", "Reloj de agujas tradicional con marcas legibles.", true),
    ANALOG_ROMAN("Analógico romano", "Estilo elegante con números romanos y diseño refinado.", true),
    FLIP_CLOCK("Flip clock retro", "Estilo clásico de paneles mecánicos que cambian.", true),
    SMART_WATCH("Smart watch", "Interfaz moderna tipo tablero con información útil.", true),
    DIVE_WATCH("Reloj de buceo", "Diseño deportivo robusto con bisel destacado.", true),
    MECHANICAL("Mecánico decorativo", "Vista artística del mecanismo interno decorativo.", true)
}
