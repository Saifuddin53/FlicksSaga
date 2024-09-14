package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.theme.Typography

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardTravelEventItem(travelEventEntity: TravelEventEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .fillMaxHeight(0.35f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFDFD)
        )
    ) {
        var columnHeightDp by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            DottedLineComposable(travelEventEntity = travelEventEntity, columnHeightDp = columnHeightDp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        val heightPx = layoutCoordinates.size.height
                        columnHeightDp = with(density) { heightPx.toDp() }
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(0.35f)
                ) {
                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .alignBy(FirstBaseline)  // Align based on the first baseline of the text
                            .padding(end = 8.dp, bottom = 8.dp)
                            .clickable { /* TODO */ },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFffffff),
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        AsyncImage(
                            model = travelEventEntity.imagesUrl[0],
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier
                            .alignBy(FirstBaseline)  // Align this column by the same baseline
                    ) {
                        Text(
                            text = travelEventEntity.title,
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight(600),
                                fontSize = 15.sp,
                                letterSpacing = 1.sp,
                            ),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StarRatingBar(
                                maxStars = 5,
                                rating = 4.2f,
                                onRatingChanged = {
                                    // Handle rating change
                                }
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
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "${formatTimestampToTime(travelEventEntity.startTimestamp)} - ${
                            formatTimestampToTime(
                                travelEventEntity.endTimestamp
                            )
                        }",
                        style = Typography.bodyMedium.copy(
                            color = Color.Black,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black,
                    )
                    Text(
                        text = getAddressFromLocation(LocalContext.current, travelEventEntity.latitude, travelEventEntity.longitude) ?: "",
                        style = Typography.bodyMedium.copy(
                            color = Color.Black,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp) // Adjust padding if needed
                    )
                }
                Box(modifier = Modifier.align(Alignment.End)) {
                    Card(
                        modifier = Modifier,
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xffF5F5F5)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Direction ",
                                style = Typography.bodyMedium.copy(
                                    fontWeight = FontWeight(500),
                                    color = Color(0xff00A3FF)
                                ),
                            )
                            Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                contentDescription = "",
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xff00A3FF))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DottedLineComposable(travelEventEntity: TravelEventEntity, columnHeightDp: Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 4.dp)
    ) {
        Box(modifier = Modifier) {
            // Marker icon at the top
            Icon(
                painter = painterResource(id = R.drawable.solid_location_pin), // Replace with your marker icon
                contentDescription = null,
                tint = Color(0xFF00A3FF),
                modifier = Modifier.size(30.dp)
            )
            Text(text = travelEventEntity.id.toString(),
                style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Dotted vertical line
        Canvas(
            modifier = Modifier
                .width(2.dp)
                .height(columnHeightDp - 90.dp)
        ) {
            val lineHeight = size.height
            drawLine(
                color = Color.Black,
                start = Offset(x = size.width / 2, y = 0f),
                end = Offset(x = size.width / 2, y = lineHeight),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f), // Dotted effect
                strokeWidth = 2f
            )
        }

        Box(
            modifier = Modifier
                .size(30.dp)
                .rotate(-90f)
                .offset(y = (-1).dp, x = (-10).dp)
        ) {
            Text(text = "Day ${travelEventEntity.id}",
                style = Typography.bodyMedium.copy(fontSize = 10.sp))
        }

        // Dotted vertical line
        Canvas(
            modifier = Modifier
                .width(2.dp)
                .height(120.dp)
                .offset(y = (-28).dp)
        ) {
            val lineHeight = size.height
            drawLine(
                color = Color.Black,
                start = Offset(x = size.width / 2, y = 0f),
                end = Offset(x = size.width / 2, y = lineHeight),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f), // Dotted effect
                strokeWidth = 2f
            )
        }
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 400,
    heightDp = 200
)
@Preview(widthDp = 400, heightDp = 200)
@Composable
fun CardTravelEventItemPreview() {
    CardTravelEventItem(
        travelEventEntity = TravelEventEntity(
            id = 1,
            title = "The GateWay of India",
            startTimestamp = 1,
            endTimestamp = 1,
            imagesUrl = listOf(),
            latitude = 18.910000,
            longitude = 72.809998,
            tags = listOf("Review", "Review", "Restaurant"),
            distance = "10m"
        )
    )
}