package com.myprojects.flickssaga.ui.components
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TypingText(text: String, typingSpeed: Long = 100L, onComplete: () -> Unit = {}) {
    var displayText by remember { mutableStateOf("") }
    LaunchedEffect(text) {
        displayText = ""
        for (i in text.indices) {
            displayText = text.substring(0, i + 1)
            delay(typingSpeed)
        }
        onComplete()
    }

    Text(displayText, style = TextStyle(color = Color.Black))
}

@Composable
fun ShimmerOutText(text: String, onComplete: () -> Unit) {
    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(text) {
        visible = true
        delay(300L) // Adjust delay as per your need
        visible = false
        delay(300L) // Adjust delay to allow shimmer out
        onComplete()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        Text(text, style = TextStyle(color = Color.Black))
    }
}

@Composable
fun AiGeneratedTextField(
    aiGeneratedText: String,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var showTypingEffect by remember { mutableStateOf(false) }
    var showShimmerEffect by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
//        BasicTextField(
//            value = text,
//            onValueChange = {},
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            textStyle = TextStyle(color = Color.Black),
//        )

        if (showTypingEffect) {
            TypingText(
                text = aiGeneratedText,
                onComplete = {
                    text = aiGeneratedText
                    showTypingEffect = false
                }
            )
        }

        if (showShimmerEffect) {
            ShimmerOutText(
                text = text,
                onComplete = {
                    showShimmerEffect = false
                    showTypingEffect = true
                }
            )
        }

        Button(
            onClick = {
                if (text.isEmpty()) {
                    showTypingEffect = true
                } else {
                    showShimmerEffect = true
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Generate AI Text")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AiGeneratedTextFieldPreview() {
    AiGeneratedTextField(aiGeneratedText = "This is AI-generated text.")
}
