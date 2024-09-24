package com.myprojects.flickssaga.ui.screens.map.trip.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun TripItem(
    travelEventEntity: TravelEventEntity,
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f),
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
                    contentScale = ContentScale.FillBounds
                )
            }
            TripItemTop(modifier = Modifier.align(Alignment.TopStart), onMoreClick)
            TripDesc(
                startTimeStamp = travelEventEntity.startTimestamp,
                endTimeStamp = travelEventEntity.endTimestamp,
                title = travelEventEntity.title,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun TripItemTop(
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Ongoing trip",
            style = Typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = Color.White
            ),
        )
        IconButton(onClick = {
            onMoreClick()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "",
                tint = Color.White,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TripDesc(
    startTimeStamp: Long,
    endTimeStamp: Long,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = "${formatTimestampToMonthDate(startTimeStamp)}-${formatTimestampToMonthDate(endTimeStamp)}",
            style = Typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            ),
        )
        Text(
            text = title,
            style = Typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
        )
        Row(
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.with_family),
                contentDescription = "",
                tint = Color.White
            )
            Text(
                text = "With Family",
                style = Typography.labelSmall.copy(
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToMonthDate(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd")
        .withZone(ZoneId.systemDefault())

    return formatter.format(Instant.ofEpochMilli(timestamp))
}


@Preview
@Composable
private fun TripItemPreview() {
    TripItem(
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