package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.theme.Typography
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TravelEventItem(travelEvent: TravelEventEntity) {
    Column(
        modifier = Modifier.fillMaxWidth()
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
                        Text(text = "${formatTimestampToTime(travelEvent.startTimestamp)} - ${formatTimestampToTime(travelEvent.endTimestamp)}",
                            style = Typography.bodyMedium.copy(Color.Black),
                            modifier = Modifier.padding(horizontal = 16.dp)
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
                        .padding(vertical = 8.dp)
                        .clickable { /* TODO */ },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFffffff),
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    ImageScrollWithTextOverlay(travelEvent.imagesUrl)
                }
                //title
                Text(text = travelEvent.title,
                    style = Typography.bodyLarge.copy(
                        fontWeight = FontWeight(600)
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), 
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
                            color = Color(0xff595959)
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(painter = painterResource(id = R.drawable.location),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
//                        Text(text = getAddressFromLocation(LocalContext.current, travelEvent.latitude, travelEvent.longitude)!!,
//                            style = Typography.bodyMedium.copy(Color.Black),
//                            modifier = Modifier.padding(horizontal = 16.dp)
//                        )
                    }
                    Row {
                        Card(
                            modifier = Modifier.padding(start = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xff00A3FF)
                            )
                        ) {
                            Icon(painter = painterResource(id = R.drawable.map_outline_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(10.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            items(travelEvent.tags) { tag ->
                TagItem(tag = tag)
            }
        }

        DirectionItem(distance = travelEvent.distance)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageScrollWithTextOverlay(images: List<String?>) {
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

        if(images.size > 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd) // Align the text to the top end of the box
                    .padding(8.dp)
                    .width(40.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                // Display the image index and total number of images
                androidx.compose.material.Text(
                    text = "${pagerState.currentPage + 1}/${images.size}", // The text to display
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp
                    ).copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
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
    val starSize = (12f * density).dp
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
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
                        fontSize = 18.sp,
                        color = Color(0xff00A3FF)
                    ),
                )
                Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
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
        travelEvent = TravelEventEntity(
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