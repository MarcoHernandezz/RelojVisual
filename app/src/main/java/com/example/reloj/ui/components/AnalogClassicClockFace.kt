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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.ZonedDateTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClassicClockFace(
    time: ZonedDateTime,
    modifier: Modifier = Modifier
) {
    val colorOnSurface = MaterialTheme.colorScheme.onSurface
    val colorPrimary = MaterialTheme.colorScheme.primary
    val colorSecondary = MaterialTheme.colorScheme.secondary

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val center = center
        val radius = size.minDimension / 2f

        drawCircle(
            color = colorOnSurface,
            radius = radius,
            center = center,
            style = Stroke(width = 4.dp.toPx())
        )

        for (i in 0 until 60) {
            val angle = i * 6f
            val isHourMark = i % 5 == 0
            val lineLength = if (isHourMark) 15.dp.toPx() else 8.dp.toPx()
            val strokeWidth = if (isHourMark) 3.dp.toPx() else 1.dp.toPx()

            rotate(angle, center) {
                drawLine(
                    color = colorOnSurface,
                    start = Offset(center.x, center.y - radius),
                    end = Offset(center.x, center.y - radius + lineLength),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }

        val textRadius = radius - 35.dp.toPx()
        val fontSizePx = 24.sp.toPx()

        val paint = Paint().apply {
            color = colorOnSurface.toArgb()
            textSize = fontSizePx
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        for (i in 1..12) {
            val angleInRad = ((i * 30 - 90) * (PI / 180f)).toFloat()
            val x = center.x + cos(angleInRad) * textRadius
            val y = center.y + sin(angleInRad) * textRadius + (fontSizePx / 3)

            drawContext.canvas.nativeCanvas.drawText(
                i.toString(),
                x,
                y,
                paint
            )
        }

        val hourAngle = (time.hour % 12) * 30f + time.minute * 0.5f
        rotate(hourAngle, center) {
            drawLine(
                color = colorOnSurface,
                start = center,
                end = Offset(center.x, center.y - radius * 0.5f),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        val minuteAngle = time.minute * 6f + time.second * 0.1f
        rotate(minuteAngle, center) {
            drawLine(
                color = colorOnSurface,
                start = center,
                end = Offset(center.x, center.y - radius * 0.75f),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        val secondAngle = time.second * 6f
        rotate(secondAngle, center) {
            drawLine(
                color = colorPrimary,
                start = center,
                end = Offset(center.x, center.y - radius * 0.85f),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        drawCircle(
            color = colorSecondary,
            radius = 6.dp.toPx(),
            center = center
        )

        drawCircle(
            color = colorOnSurface,
            radius = 3.dp.toPx(),
            center = center
        )
    }
}