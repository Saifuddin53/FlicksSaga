package com.myprojects.flickssaga.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import kotlinx.coroutines.delay

@Composable
fun CustomCircularProgressBar(
    progress: Float, // Value between 0.0 and 1.0
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = Color.Gray,
    strokeWidth: Float = 8f,
    showPercentage: Boolean = true,
) {
    if (progress < 1f) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp)
        ) {
            Canvas(modifier = Modifier.size(100.dp)) {
                // Draw the track
                drawArc(
                    color = trackColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
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
                painter = painterResource(id = R.drawable.__1_),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
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