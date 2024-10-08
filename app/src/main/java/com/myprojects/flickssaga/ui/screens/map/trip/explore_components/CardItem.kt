package com.myprojects.flickssaga.ui.screens.map.trip.explore_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.ui.screens.map.trip.components.RestaurantDesc
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun CardItem(
    category: CategoryItemData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .height(180.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Load the image asynchronously
                AsyncImage(
                    model = category.image, // The URL of the image
                    contentDescription = null, // No content description is provided
                    modifier = Modifier.fillMaxSize(), // Fill the width of the parent
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = category.title,
                    style = Typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }
    }
}