package com.example.reloj.ui.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reloj.ui.ClockStyleSettings
import java.time.ZonedDateTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MechanicalClockFace(
    time: ZonedDateTime,
    settings: ClockStyleSettings,
    modifier: Modifier = Modifier
) {
    val preset = settings.colorPreset
    val mainColor = if (preset.primaryColor == Color.Transparent) MaterialTheme.colorScheme.onSurface else preset.primaryColor
    val secondaryColor = if (preset.secondaryColor == Color.Transparent) MaterialTheme.colorScheme.secondary else preset.secondaryColor
    val accentColor = if (preset.accentColor == Color.Transparent) MaterialTheme.colorScheme.primary else preset.accentColor

    Canvas(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val center = center
        val radius = size.minDimension / 2f
        
        // 1. Círculo exterior elegante
        drawCircle(
            color = mainColor,
            radius = radius,
            style = Stroke(width = 3.dp.toPx())
        )

        // 2. Engranes decorativos (Skeleton look)
        // Engrane Central
        drawGear(center, radius * 0.4f, 12, mainColor.copy(alpha = 0.2f), 2.dp.toPx())
        
        // Engrane Secundario (arriba a la derecha)
        drawGear(
            Offset(center.x + radius * 0.35f, center.y - radius * 0.35f),
            radius * 0.25f,
            10,
            secondaryColor.copy(alpha = 0.15f),
            1.5.dp.toPx()
        )
        
        // Engrane Terciario (abajo a la izquierda)
        drawGear(
            Offset(center.x - radius * 0.3f, center.y + radius * 0.4f),
            radius * 0.2f,
            8,
            accentColor.copy(alpha = 0.1f),
            1.dp.toPx()
        )

        // 3. Marcas de horas
        for (i in 0 until 12) {
            val angle = i * 30f
            rotate(angle, center) {
                drawLine(
                    color = mainColor,
                    start = Offset(center.x, center.y - radius),
                    end = Offset(center.x, center.y - radius + 15.dp.toPx()),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // 4. Marcas de minutos (si está habilitado)
        if (settings.showAnalogMinuteMarks) {
            for (i in 0 until 60) {
                if (i % 5 != 0) {
                    rotate(i * 6f, center) {
                        drawLine(
                            color = mainColor.copy(alpha = 0.4f),
                            start = Offset(center.x, center.y - radius),
                            end = Offset(center.x, center.y - radius + 7.dp.toPx()),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            }
        }

        // 5. Números decorativos
        if (settings.showAnalogNumbers) {
            val paint = Paint().apply {
                color = mainColor.toArgb()
                textSize = 20.sp.toPx()
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            }
            val textRadius = radius - 35.dp.toPx()
            for (i in 1..12) {
                val angleInRad = (i * 30 - 90) * (PI / 180f).toFloat()
                val x = center.x + cos(angleInRad) * textRadius
                val y = center.y + sin(angleInRad) * textRadius + (paint.textSize / 3)
                drawContext.canvas.nativeCanvas.drawText(i.toString(), x, y, paint)
            }
        }

        // 6. Manecillas elegantes
        val hourAngle = (time.hour % 12) * 30f + time.minute * 0.5f
        val minuteAngle = time.minute * 6f + time.second * 0.1f

        // Hora
        rotate(hourAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - radius * 0.55f),
                strokeWidth = 5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Minuto
        rotate(minuteAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - radius * 0.85f),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Segundo
        if (settings.showSeconds) {
            val secondAngle = time.second * 6f
            rotate(secondAngle, center) {
                drawLine(
                    color = accentColor,
                    start = Offset(center.x, center.y + 20.dp.toPx()),
                    end = Offset(center.x, center.y - radius * 0.9f),
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawCircle(color = accentColor, radius = 4.dp.toPx(), center = center)
            }
        }

        // 7. Punto central (Tornillo)
        drawCircle(color = mainColor, radius = 7.dp.toPx(), center = center)
        drawCircle(color = secondaryColor, radius = 3.dp.toPx(), center = center, style = Stroke(1.dp.toPx()))
    }
}

private fun DrawScope.drawGear(
    center: Offset,
    radius: Float,
    teethCount: Int,
    color: Color,
    strokeWidth: Float
) {
    // Cuerpo del engrane
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )
    
    // Dientes del engrane
    val toothLength = 6.dp.toPx()
    for (i in 0 until teethCount) {
        val angle = (i * 360f / teethCount)
        rotate(angle, center) {
            drawLine(
                color = color,
                start = Offset(center.x, center.y - radius),
                end = Offset(center.x, center.y - radius - toothLength),
                strokeWidth = strokeWidth * 2
            )
        }
    }
    
    // Interior del engrane (Eje)
    drawCircle(
        color = color,
        radius = radius * 0.2f,
        center = center,
        style = Stroke(width = strokeWidth)
    )
}
