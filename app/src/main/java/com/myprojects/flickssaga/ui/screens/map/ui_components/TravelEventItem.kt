package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myprojects.flickssaga.DetailItem
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TravelEventItem(
    travelEventEntity: TravelEventEntity,
    navController: NavHostController,
    ) {
    var columnHeightDp by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                    .height(columnHeightDp + 70.dp)
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
                    .background(color = Color(0xFF00A3FF), shape = RoundedCornerShape(10.dp))
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.direciton_fill), // Replace with your marker icon
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }

            // Dotted vertical line
            Canvas(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 8.dp)
                .clickable {
                    navController.navigate(DetailItem(travelEventEntity.id))
                }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffF5F5F5)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            val heightPx = layoutCoordinates.size.height
                            columnHeightDp = with(density) { heightPx.toDp() }
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.clock),
                                contentDescription = "",
                                tint = Color.Black)
                            Text(text = "${formatTimestampToTime(travelEventEntity.startTimestamp)} - ${formatTimestampToTime(travelEventEntity.endTimestamp)}",
                                style = Typography.bodyMedium.copy(Color.Black),
                                modifier = Modifier.padding(horizontal = 14.dp)
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                                contentDescription = "",
                                tint = Color.Black)
                        }
                    }
                    //image
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(bottom = 8.dp)
                            .clickable { /* TODO */ },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFffffff),
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        ImageScrollWithTextOverlay(travelEventEntity.imagesUrl)
                    }
                    //title
                    Text(text = travelEventEntity.title,
                        style = Typography.bodyLarge.copy(
                            fontWeight = FontWeight(600)
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
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
                        Text(text = "20,320 Reviews",
                            style = Typography.bodyMedium.copy(
                                fontWeight = FontWeight(600),
                                color = Color(0xff595959),
                                fontSize = 10.sp
                            ),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .align(Alignment.Bottom)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(0.9f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = getAddressFromLocation(LocalContext.current, travelEventEntity.latitude, travelEventEntity.longitude) ?: "",
                                style = Typography.bodyMedium.copy(Color.Black),
                                modifier = Modifier.padding(horizontal = 8.dp) // Adjust padding if needed
                            )
                        }

                        Card(
                            modifier = Modifier,
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff00A3FF)
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.map_outline_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(8.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                items(travelEventEntity.tags) { tag ->
                    TagItem(tag = tag)
                }
            }

            DirectionItem(distance = travelEventEntity.distance)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageScrollWithTextOverlay(images: List<String?>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Load the image asynchronously
                AsyncImage(
                    model = images[page], // The URL of the image
                    contentDescription = null, // No content description is provided
                    modifier = Modifier.fillMaxSize(), // Fill the width of the parent
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Show dots at the bottom center of the image
        if (images.size > 1) {
            Row(
                modifier = modifier
                    .align(Alignment.BottomCenter) // Align the row to the bottom center of the box
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Iterate through the images and show a dot for each image
                images.forEachIndexed { index, _ ->
                    val color = if (pagerState.currentPage == index) Color.White else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .size(6.dp)
                            .background(color, shape = CircleShape)
                    )
                }
            }
        }
    }
}


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (8f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)

            Box(
                modifier = Modifier
                    .width(starSize)
                    .height(starSize)
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
            ) {
                // Background star (unfilled)
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0x20FFFFFF), // Unfilled color
                    modifier = Modifier
                        .fillMaxSize()
                )

                // Foreground star (filled with clipping)
                if (i <= rating) {
                    val fraction = if (i < rating) 1f else rating - i + 1
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC700), // Filled color
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RectangleShape)
                            .width(starSize * fraction)
                    )
                }
            }

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Composable
fun TagItem(tag: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF5F5F5)
        )
    ) {
        Text(text = tag,
            style = Typography.bodyMedium.copy(
                fontWeight = FontWeight(500)
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
    }
}

@Composable
fun DirectionItem(distance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.car_outline),
                contentDescription = "",
                modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = distance,
                style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Direction ",
                    style = Typography.bodyMedium.copy(
                        fontWeight = FontWeight(500),
                        fontSize = 16.sp,
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

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToTime(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        .withZone(ZoneId.systemDefault())

    return formatter.format(Instant.ofEpochMilli(timestamp)).uppercase()
}

fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)

    // Check if address is not empty
    return if (addresses?.isNotEmpty() == true) {
        addresses[0].getAddressLine(0) // Returns the first address line
    } else {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TravelEventItemPreview() {
    TravelEventItem(
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
        ),
        navController = NavHostController(LocalContext.current)
    )
}