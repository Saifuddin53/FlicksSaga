package com.myprojects.flickssaga.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R

@Composable
fun CustomCircularProgressBar(
    progress: Float, // Value between 0.0 and 1.0
    color: Color = Color.Red,
    trackColor: Color = Color.Gray,
    showPercentage: Boolean = false,
    circleSize: Dp = 50.dp
) {
    val strokeWidth = with(LocalDensity.current) { circleSize.toPx() / 2 }
    if (progress < 1f) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(circleSize)
        ) {
            Canvas(modifier = Modifier.size(circleSize)) {
                // Draw the track
                drawArc(
                    color = trackColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = true,
                    size = Size(circleSize.toPx(), circleSize.toPx()),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Square)
                )

                // Draw the progress
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.bomb),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.7f)
            )

            if (showPercentage) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
            }
        }
    }
}