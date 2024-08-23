package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.ItineraryEntity
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineItineraryContent(contentHeight: Float, imgAlpha: Float) {
    Box(modifier = Modifier.fillMaxWidth().height(contentHeight.dp)) {
        Image(
            painter = painterResource(id = R.drawable.map_image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(if (contentHeight > 205) 200.dp else contentHeight.dp)
                .graphicsLayer { alpha = imgAlpha },
            contentScale = ContentScale.FillWidth
        )

        ItineraryDetailItem(
            itinerary = ItineraryEntity(
                "Mumbai",
                "Maharashtra",
                "India",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 3),
                listOf(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), LocalDate.of(2023, 1, 3)),
                listOf()
            )
        )

        Card(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
                .size(36.dp)
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(15.dp),
                    tint = Color.Black
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(top = 30.dp, end = 20.dp)
                .size(36.dp)
                .align(Alignment.TopEnd),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.map_outline_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(16.dp),
                    tint = Color.Black
                )
            }
        }
    }
}
