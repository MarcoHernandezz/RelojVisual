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
fun AnalogRomanClockFace(
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
        .padding(24.dp)) {
        val center = center
        val radius = size.minDimension / 2f
        
        // 1. Esfera exterior elegante (doble círculo fino)
        drawCircle(
            color = mainColor,
            radius = radius,
            style = Stroke(width = 1.5.dp.toPx())
        )
        drawCircle(
            color = mainColor,
            radius = radius - 4.dp.toPx(),
            style = Stroke(width = 0.5.dp.toPx())
        )

        // 2. Marcas de horas y minutos
        for (i in 0 until 60) {
            val angle = i * 6f
            val isHourMark = i % 5 == 0
            
            if (isHourMark) {
                rotate(angle, center) {
                    drawLine(
                        color = mainColor,
                        start = Offset(center.x, center.y - radius + 5.dp.toPx()),
                        end = Offset(center.x, center.y - radius + 15.dp.toPx()),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Square
                    )
                }
            } else if (settings.showAnalogMinuteMarks) {
                rotate(angle, center) {
                    drawLine(
                        color = mainColor.copy(alpha = 0.5f),
                        start = Offset(center.x, center.y - radius + 5.dp.toPx()),
                        end = Offset(center.x, center.y - radius + 8.dp.toPx()),
                        strokeWidth = 0.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        // 3. Números Romanos
        if (settings.showAnalogNumbers) {
            val romanNumerals = listOf("XII", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI")
            val textRadius = radius - 35.dp.toPx()
            val fontSize = 22.sp.toPx()
            val paint = Paint().apply {
                color = mainColor.toArgb()
                textSize = fontSize
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            }

            for (i in 0 until 12) {
                val angleInRad = (i * 30 - 90) * (PI / 180f).toFloat()
                val x = center.x + cos(angleInRad) * textRadius
                val y = center.y + sin(angleInRad) * textRadius + (fontSize / 3)
                drawContext.canvas.nativeCanvas.drawText(romanNumerals[i], x, y, paint)
            }
        }

        // 4. Manecillas de estilo clásico
        
        // Horas
        val hourAngle = (time.hour % 12) * 30f + time.minute * 0.5f
        rotate(hourAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - radius * 0.55f),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Minutos
        val minuteAngle = time.minute * 6f + time.second * 0.1f
        rotate(minuteAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - radius * 0.8f),
                strokeWidth = 2.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Segundos
        if (settings.showSeconds) {
            val secondAngle = time.second * 6f
            rotate(secondAngle, center) {
                drawLine(
                    color = accentColor,
                    start = Offset(center.x, center.y + 15.dp.toPx()), // Pequeño contrapeso
                    end = Offset(center.x, center.y - radius * 0.9f),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // 5. Centro elegante
        drawCircle(
            color = mainColor,
            radius = 5.dp.toPx(),
            center = center
        )
        drawCircle(
            color = secondaryColor,
            radius = 2.dp.toPx(),
            center = center
        )
    }
}
