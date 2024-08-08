package com.myprojects.flickssaga.ui.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AnimatedTextField(
    title: String = strings[0],
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var index by remember { mutableStateOf(0) }

    var lineAnimation by remember { mutableStateOf(false) }
    var gifAnimation by remember { mutableStateOf(false) }

    var typingState by remember { mutableStateOf(true) }
    var isAnimating by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = tween(durationMillis = 1500) // 1.5 seconds for full rotation
    )
    val backwardTilt by animateFloatAsState(
        targetValue = if (isAnimating) 350f else 0f,
        animationSpec = tween(durationMillis = 1500) // Same duration for backward tilt
    )

    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        ),
        label = ""
    )

    val gradientColors = listOf(Color(0xFF42A5F5), Color(0xFF478ED1), Color(0xFF3A75C4))

    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.output_onlinegiftools) // Reference to the raw resource
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    val painter = rememberImagePainter(request = imageRequest)


    fun startTypingEffect() {
        typingState = false
        lineAnimation = true
        coroutineScope.launch {
            if(currentText.isNotEmpty()) {
                gifAnimation = true
                for (char in currentText) {
                    currentText = currentText.dropLast(1)
                    delay(100) // Adjust typing speed
                }
                gifAnimation = false
            }
            currentText = ""
            for (char in strings[index]) {
                currentText += char
                delay(100) // Adjust typing speed
            }
            typingState = true
            lineAnimation = false
            index++
            onValueChange(currentText)
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp))
    {
        OutlinedTextField(
            value = currentText,
            onValueChange = { currentText = it },
            placeholder = {
                Text(
                    text = "Title",
                    color = Color.Gray,
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp
                    )
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    isAnimating = !isAnimating
                    if (typingState) {
                        startTypingEffect()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.magic), // Replace with your icon
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                            .graphicsLayer(
                                rotationX = backwardTilt,
                                rotationZ = rotationAngle
                            ),
                        tint = gradientColors[2]
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = gradientColors[2],
                unfocusedBorderColor = gradientColors[2]
            ),
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                color = gradientColors[1]
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .drawBehind {
                    val strokeWidth = 5.dp.toPx()
                    val pathLength = size.width * 2 + size.height * 2
                    val currentLength = animatedProgress * pathLength
                    val lineWidth = 100.dp.toPx()

                    val bottomRight = Offset(size.width, size.height)
                    val bottomLeft = Offset(0f, size.height)

                    if (lineAnimation) {
                        // Top line
                        if (currentLength <= size.width) {
                            drawLine(
                                brush = Brush.linearGradient(
                                    gradientColors
                                ),
                                start = Offset(currentLength - lineWidth, 0f).takeIf { it.x >= 0 }
                                    ?: Offset(0f, 0f),
                                end = Offset(currentLength, 0f),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }

                        // Right line
                        if (currentLength > size.width && currentLength <= size.width + size.height) {
                            val adjustedLength = currentLength - size.width
                            drawLine(
                                brush = Brush.linearGradient(
                                    gradientColors
                                ),
                                start = Offset(
                                    size.width,
                                    adjustedLength - lineWidth
                                ).takeIf { it.y >= 0 } ?: Offset(size.width, 0f),
                                end = Offset(size.width, adjustedLength),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }

                        // Bottom line
                        if (currentLength > size.width + size.height && currentLength <= 2 * size.width + size.height) {
                            val adjustedLength = currentLength - size.width - size.height
                            drawLine(
                                brush = Brush.linearGradient(
                                    gradientColors
                                ),
                                start = Offset(
                                    size.width - adjustedLength + lineWidth,
                                    size.height
                                ).takeIf { it.x <= size.width } ?: bottomRight,
                                end = Offset(size.width - adjustedLength, size.height),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }

                        // Left line
                        if (currentLength > 2 * size.width + size.height) {
                            val adjustedLength = currentLength - 2 * size.width - size.height
                            drawLine(
                                brush = Brush.linearGradient(
                                    gradientColors
                                ),
                                start = Offset(
                                    0f,
                                    size.height - adjustedLength + lineWidth
                                ).takeIf { it.y <= size.height } ?: bottomLeft,
                                end = Offset(0f, size.height - adjustedLength),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
        )

        if (gifAnimation) {
            val charsPerLine = 36 // Approximate number of characters per line (adjust based on actual layout)

            // Calculate the current line number and the character position on that line
            val currentLine = (currentText.length / charsPerLine) + 1

            val lineCharLength = currentText.length - ((currentLine - 1) * charsPerLine)


            val maxOffset = 280.dp // Adjust this value based on your text field width
            val animatedOffset by animateDpAsState(
                targetValue = maxOffset - if(currentText.length < 36) (currentText.length*10).dp else (lineCharLength * 10).dp,
                animationSpec = tween(durationMillis = 100, easing = LinearEasing)
            )

            val numberOfLines = getEstimatedNumberOfLines(currentText, TextStyle(fontSize = 16.sp), 300.dp)
            val clampedOffset = (maxOffset - animatedOffset).coerceIn(0.dp, maxOffset)
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .offset(
                            x = clampedOffset,
                            y = if (numberOfLines > 1) ((currentLine - 1) * 18).dp else 0.dp
                        ) // Adjust the y-offset based on the current line
                        .size(40.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }


//
//            Box(
//                modifier = Modifier
//                    .padding(top = 8.dp)
//                    .fillMaxWidth()
//            ) {
//                Image(
//                    painter = painter,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .offset(x = maxOffset - animatedOffset, y = if (animatedOffset < 0.dp) 40.dp else 0.dp) // Adjust this to your requirement
//                        .size(40.dp)
//                        .clip(RoundedCornerShape(6.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
    }
}

@Composable
fun calculateCharsPerLine(): Int {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    val fontSizeSp = 16.sp // Your font size in sp

    // Convert sp to dp
    val density = LocalDensity.current
    val charWidthDp: Dp = with(density) { fontSizeSp.toDp() }

    return (screenWidthDp / charWidthDp).toInt()
}

@Composable
fun getEstimatedNumberOfLines(text: String, textStyle: TextStyle, textFieldWidth: Dp): Int {
    val charWidth = with(LocalDensity.current) { textStyle.fontSize.toPx() / 2 } // Approximate average width of a character
    val maxCharsPerLine = with(LocalDensity.current) { textFieldWidth.toPx() / charWidth }.toInt()

    return if (maxCharsPerLine > 0) {
        val totalChars = text.length
        val estimatedLines = (totalChars / maxCharsPerLine) + 1
        estimatedLines
    } else {
        1
    }
}

val strings = listOf(
    "This is string 1",
    "This is string 2 and this is too long and long  ",
    "This is string 3 and longgg",
    "This is string 4 and this is too long",
    "This is string 5",
    "This is string 6",
    "This is string 7",
    "This is string 8",
)