package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.ItineraryEntity
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItineraryDetailItem(itinerary: ItineraryEntity) {
    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 150.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "${itinerary.city}, ${itinerary.state}",
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight(600)
                            )
                        )
                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = itinerary.country,
                                style = Typography.bodyMedium
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "",
                                modifier = Modifier.padding(start = 14.dp, end = 4.dp)
                                    .size(18.dp)
                            )
                            Text(
                                text = "${itinerary.days.size}-Day",
                                style = Typography.bodyMedium
                            )
                        }
                    }
                    //image
                    AsyncImage(
                        model = "https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg",
                        contentDescription = "user image",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                    ) {
                        Card(
                            modifier = Modifier,
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(width = 1.dp, brush = Brush.linearGradient(listOf(Color.LightGray, Color.LightGray))), colors = CardDefaults.cardColors(
                                containerColor = Color(0xffF5F5F5)
                            )

                        ) {
                            Text(text = "${formatDate(itinerary.startDate)}  ------->  ${formatDate(itinerary.endDate)}",
                                style = Typography.bodyMedium.copy(
                                    fontSize = 10.sp,
                                    color = Color(0xff595959)
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                        Card(
                            modifier = Modifier.padding(start = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(width = 1.dp, brush = Brush.linearGradient(listOf(Color.LightGray, Color.LightGray))),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xffF5F5F5)
                            )
                        ) {
                            Icon(painter = painterResource(id = R.drawable.pen_edit),
                                contentDescription = "",
                                modifier = Modifier.padding(6.dp))
                        }
                    }
                    Card(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(width = 1.dp, brush = Brush.linearGradient(listOf(Color.LightGray, Color.LightGray))),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xffF5F5F5)
                        )
                    ) {
                        Icon(painter = painterResource(id = R.drawable.bookmark),
                            contentDescription = "",
                            modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("DD MMM yy")
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItineraryDetailItemPreview() {
    ItineraryDetailItem(itinerary =
    ItineraryEntity("Mumbai",
            "Maharashtra",
            "India",
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 1, 3),
            listOf(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), LocalDate.of(2023, 1, 3)),
            listOf()
        )
    )
}