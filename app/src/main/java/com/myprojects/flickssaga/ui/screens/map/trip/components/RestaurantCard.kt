package com.myprojects.flickssaga.ui.screens.map.trip.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.StarRating
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.ui_components.StarRatingBar
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun RestaurantCard(travelEventEntity: TravelEventEntity,modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Load the image asynchronously
                AsyncImage(
                    model = travelEventEntity.imagesUrl[0], // The URL of the image
                    contentDescription = null, // No content description is provided
                    modifier = Modifier.fillMaxSize(), // Fill the width of the parent
                    contentScale = ContentScale.FillHeight
                )
            }
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .background(
                            color = Color(0xff00A3FF),
                            shape = CircleShape
                        )
                        .size(42.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_upload),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            RestaurantDesc(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 30.dp)
            )
        }
    }
}

@Composable
fun RestaurantDesc(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = "The bombay canteen",
            style = Typography.bodyMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White
            ),
        )

        Row(
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Text(
                text = "4.5",
                style = Typography.bodyMedium.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White,
                ),
                modifier = Modifier
                    .padding(top = 2.dp, end = 6.dp)
                    .background(
                        color = Color(0xff339D3A),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )

            StarRatingBar(
                rating = 4.5f,
                onRatingChanged = {},
                modifier = Modifier
            )


            Text(
                text = "20,320 Reviews",
                style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight(600),
                    color = Color(0xff595959),
                    fontSize = 10.sp
                ),
                modifier = Modifier.align(Alignment.Bottom)
            )
        }

        Text(
            text = "Bachelors Juice center is very\n" +
                    "famous juice center Located in \n" +
                    "mumbai. ",
            style = Typography.bodyLarge.copy(
                fontWeight = FontWeight(500),
                color = Color.White
            ),
        )
    }
}