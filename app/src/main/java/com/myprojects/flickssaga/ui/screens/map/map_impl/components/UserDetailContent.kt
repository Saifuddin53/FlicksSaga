package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun UserDetailContent(
    name: String,
    username: String,
    profilePictureUrl: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(40.dp)
        )
        Column(

        ) {
            Text(
                text = name,
                style = Typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xff2D2D2D)
                )
            )
            Text(
                text = username,
                style = Typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xff595959)
                )
            )
        }
    }
}