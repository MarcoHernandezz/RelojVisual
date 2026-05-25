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
import androidx.compose.ui.graphics.Path
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
fun DiveWatchClockFace(
    time: ZonedDateTime,
    settings: ClockStyleSettings,
    modifier: Modifier = Modifier
) {
    val preset = settings.colorPreset
    val mainColor = if (preset.primaryColor == Color.Transparent) MaterialTheme.colorScheme.onSurface else preset.primaryColor
    val accentColor = if (preset.accentColor == Color.Transparent) MaterialTheme.colorScheme.primary else preset.accentColor

    Canvas(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val center = center
        val canvasSize = size.minDimension
        val outerRadius = canvasSize / 2f
        val bezelThickness = 22.dp.toPx()
        val innerRadius = outerRadius - bezelThickness

        // 1. Bisel exterior grueso
        drawCircle(
            color = mainColor,
            radius = outerRadius - (bezelThickness / 2),
            style = Stroke(width = bezelThickness)
        )

        // 2. Marca triangular a las 12 en el bisel
        val trianglePath = Path().apply {
            moveTo(center.x, center.y - outerRadius + 4.dp.toPx())
            lineTo(center.x - 10.dp.toPx(), center.y - outerRadius + 18.dp.toPx())
            lineTo(center.x + 10.dp.toPx(), center.y - outerRadius + 18.dp.toPx())
            close()
        }
        drawPath(
            path = trianglePath,
            color = if (preset.backgroundColor == Color.Black) Color.White else Color.Black
        )

        // 3. Esfera
        drawCircle(
            color = mainColor.copy(alpha = 0.05f),
            radius = innerRadius
        )

        // 4. Marcas de horas (Grandes y circulares tipo "lume")
        for (i in 0 until 12) {
            val angle = i * 30f
            rotate(angle, center) {
                drawCircle(
                    color = mainColor,
                    radius = 5.dp.toPx(),
                    center = Offset(center.x, center.y - innerRadius + 18.dp.toPx())
                )
            }
        }

        // 5. Marcas de minutos
        if (settings.showAnalogMinuteMarks) {
            for (i in 0 until 60) {
                if (i % 5 != 0) {
                    rotate(i * 6f, center) {
                        drawLine(
                            color = mainColor.copy(alpha = 0.4f),
                            start = Offset(center.x, center.y - innerRadius + 5.dp.toPx()),
                            end = Offset(center.x, center.y - innerRadius + 12.dp.toPx()),
                            strokeWidth = 1.2.dp.toPx()
                        )
                    }
                }
            }
        }

        // 6. Números deportivos (12, 3, 6, 9)
        if (settings.showAnalogNumbers) {
            val paint = Paint().apply {
                color = mainColor.toArgb()
                textSize = 30.sp.toPx()
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            }
            val textRadius = innerRadius - 40.dp.toPx()
            val hours = mapOf(0 to "12", 3 to "3", 6 to "6", 9 to "9")
            
            hours.forEach { (h, txt) ->
                val rad = (h * 30 - 90) * (PI / 180f).toFloat()
                val x = center.x + cos(rad) * textRadius
                val y = center.y + sin(rad) * textRadius + (paint.textSize / 3)
                drawContext.canvas.nativeCanvas.drawText(txt, x, y, paint)
            }
        }

        // 7. Manecillas gruesas y robustas
        val hourAngle = (time.hour % 12) * 30f + time.minute * 0.5f
        val minuteAngle = time.minute * 6f + time.second * 0.1f

        // Hora (Corta y muy gruesa)
        rotate(hourAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - innerRadius * 0.55f),
                strokeWidth = 10.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Minuto (Larga y gruesa)
        rotate(minuteAngle, center) {
            drawLine(
                color = mainColor,
                start = center,
                end = Offset(center.x, center.y - innerRadius * 0.85f),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Segundo
        if (settings.showSeconds) {
            rotate(time.second * 6f, center) {
                drawLine(
                    color = accentColor,
                    start = center,
                    end = Offset(center.x, center.y - innerRadius * 0.92f),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
                // Círculo decorativo en el segundero
                drawCircle(
                    color = accentColor,
                    radius = 4.dp.toPx(),
                    center = Offset(center.x, center.y - innerRadius * 0.75f)
                )
            }
        }

        // 8. Punto central
        drawCircle(color = mainColor, radius = 7.dp.toPx(), center = center)
    }
}
