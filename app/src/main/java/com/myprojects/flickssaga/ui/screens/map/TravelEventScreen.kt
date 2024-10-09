package com.myprojects.flickssaga.ui.screens.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.DetailItem
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.ui_components.ImageScrollWithTextOverlay
import com.myprojects.flickssaga.ui.screens.map.ui_components.StarRatingBar
import com.myprojects.flickssaga.ui.screens.map.ui_components.formatTimestampToTime
import com.myprojects.flickssaga.ui.screens.map.ui_components.getAddressFromLocation
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedTransitionScope.TravelEventScreen(
    id: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val travelEvent = days[0].travelEvents[id]
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clickable { /* TODO */ },
                shape = RoundedCornerShape(bottomEnd = 0.dp, bottomStart = 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFffffff),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
            ) {
                ImageScrollWithTextOverlay(travelEvent.imagesUrl, modifier = Modifier.padding(bottom = 32.dp))
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-28).dp)
                    .height(600.dp)
                    .clickable { /* TODO */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFffffff),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                ) {
                    //title
                    item { Text(text = travelEvent.title,
                        style = Typography.bodyLarge.copy(
                            fontWeight = FontWeight(600)
                        ),
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
                    ) }
                    item { Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StarRatingBar(
                            maxStars = 5,
                            rating = 5f,
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
                    } }
                    item { TextWithIcon(icon = R.drawable.visitors, text = "1 Million + People Visit/Month") }
                    item { TextWithIcon(icon = R.drawable.location, text = getAddressFromLocation(LocalContext.current, travelEvent.latitude, travelEvent.longitude) ?: "") }
                    item { TextWithIcon(icon = R.drawable.clock, text = "${formatTimestampToTime(travelEvent.startTimestamp)} - ${formatTimestampToTime(travelEvent.endTimestamp)}") }
                    item { TextWithIcon(icon = R.drawable.calendar, text = formatDate(LocalDate.now())) }
                    item { ExpandableText(text = "The Gateway of India is an arch-monument completed in 1924 on the waterfront of Mumbai (Bombay), India. It was erected to commemorate the landing of George V for his coronation as the Emperor of India in December 1911 at Strand Road near Wellington Fountain. He was the first British monarch to visit India.") }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Post related to your Mutual",
                                style = Typography.bodyMedium.copy(fontWeight = FontWeight(600)),
                            )
                            Text(text = "See all",
                                style = Typography.bodyMedium,
                                color = Color(0xff00A3FF),)
                        }
                    }
                    item {
                        LazyRow {
                            items(images) {
                                ImageItem(it)
                            }
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)  // Static position
                .size(36.dp)
                .align(Alignment.TopStart)
                .sharedElement(
                    state = rememberSharedContentState(key = "icon/${travelEvent.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
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
                .padding(top = 30.dp, end = 20.dp)  // Static position
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

@Composable
fun TextWithIcon(
    icon: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            style = Typography.bodyMedium.copy(Color.Black),
            modifier = Modifier.padding(horizontal = 8.dp) // Adjust padding if needed
        )
    }
}

@Composable
fun ExpandableText(text: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxLines = if (isExpanded) Int.MAX_VALUE else 2

    Box(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = if(isExpanded) text else text.take(70),
            style = Typography.bodyMedium,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        if (!isExpanded) {
            TextButton(onClick = { isExpanded = true }, modifier = Modifier
                .padding(top = 4.dp, end = 32.dp)
                .align(Alignment.BottomEnd)) {
                Text(text = ".... Read More",
                    style = Typography.bodyMedium,
                    color = Color(0xff00A3FF),)
            }
        }
    }
}

@Composable
fun ImageItem(url: String) {
    Card(
        modifier = Modifier
            .height(120.dp)
            .width(100.dp)
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .clickable { /* TODO */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFffffff),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        AsyncImage(
            model = url, // The URL of the image
            contentDescription = null, // No content description is provided
            modifier = Modifier.fillMaxSize(), // Fill the width of the parent
            contentScale = ContentScale.Crop
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d")
    return date.format(formatter)
}

val images = listOf("https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
    "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
    "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
    "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
    "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
    "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls="
)
