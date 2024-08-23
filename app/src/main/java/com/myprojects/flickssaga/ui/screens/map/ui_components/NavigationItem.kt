package com.myprojects.flickssaga.ui.screens.map.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun NavigationItem(
    onTextClick: (String) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf("itinerary") }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = {
            if(selectedItem != "itinerary") {
                selectedItem = "itinerary"
                onTextClick(selectedItem)
            }
        },
            modifier = Modifier
                .padding(horizontal = 8.dp) // Adjust padding if needed
                .drawBehind {
                    if (selectedItem == "itinerary") {
                        val strokeWidth = 4.dp.toPx() // Border thickness
                        val y = size.height - strokeWidth / 2 // Position the line at the bottom
                        drawLine(
                            color = Color(0xFF00A3FF),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                }) {
            Text(text = "Itinerary",
                style = Typography.bodyMedium.copy(
                    color = if (selectedItem == "itinerary") Color(0xFF00A3FF) else Color.Black
                )
            )
        }
        TextButton(onClick = {
            if(selectedItem != "explore") {
                selectedItem = "explore"
                onTextClick(selectedItem)
            }
        },
            modifier = Modifier
                .padding(horizontal = 8.dp) // Adjust padding if needed
                .drawBehind {
                    if (selectedItem == "explore") {
                        val strokeWidth = 4.dp.toPx() // Border thickness
                        val y = size.height - strokeWidth / 2 // Position the line at the bottom
                        drawLine(
                            color = Color(0xFF00A3FF),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                }) {
            Text(text = "Explore",
                style = Typography.bodyMedium.copy(
                    color = if (selectedItem == "explore") Color(0xFF00A3FF) else Color.Black
                )
            )
        }
    }
}

@Preview
@Composable
fun NavigationItemPreview() {
    NavigationItem()
}

sealed class NavigationItemComponents(val route: String, val title: String) {
    object Itinerary : NavigationItemComponents("itinerary", "Itinerary")
    object Explore : NavigationItemComponents("explore", "Explore")
}