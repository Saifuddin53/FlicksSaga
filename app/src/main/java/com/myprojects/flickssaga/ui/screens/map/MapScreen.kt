package com.myprojects.flickssaga.ui.screens.map

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.screens.map.models.DayTimeLineEntity
import com.myprojects.flickssaga.ui.screens.map.models.ItineraryEntity
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.ui_components.DayHorizontalScrollItem
import com.myprojects.flickssaga.ui.screens.map.ui_components.NavigationItem
import com.myprojects.flickssaga.ui.screens.map.ui_components.TimelineItineraryContent
import com.myprojects.flickssaga.ui.screens.map.ui_components.TravelEventItem
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = { Text("Itinerary") },
//                navigationIcon = {
//                    Icon(Icons.Default.ArrowBack, contentDescription = null)
//                },
//                actions = { /* Any action buttons */ }
//            )
        },
        floatingActionButton = {
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { padding ->
        TimelineScreen(modifier = Modifier.padding(padding), navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("itinerary") }
    var currentImgSize by remember { mutableStateOf(300f) }
    var currentImgAlpha by remember { mutableStateOf(1f) }
    var isTopBarVisible by remember { mutableStateOf(false) }
    var isScrollingBlocked by remember { mutableStateOf(false) }
    var currentSelectedDate = remember { mutableStateOf(days.get(0)) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (isScrollingBlocked) return Offset.Zero

                val delta = available.y.toInt()
                val newImgSize = currentImgSize + delta
                val previousImgSize = currentImgSize
                currentImgSize = newImgSize.coerceIn(25f, 300f)
                val consumed = currentImgSize - previousImgSize

                val sizeDelta = currentImgSize / 300f
                currentImgAlpha = sizeDelta.coerceIn(0f, 1f)

                isTopBarVisible = currentImgSize <= 25f
                isScrollingBlocked = isTopBarVisible

                return Offset(0f, consumed)
            }
        }
    }

    Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, if (currentImgSize > 30) (currentImgSize).dp else 100.dp)
                .padding(bottom = 20.dp),
            userScrollEnabled = !isScrollingBlocked // Disable scrolling when the top bar is visible
        ) {
            item {
                NavigationItem { selectedItem = it }
            }
            if (selectedItem == "itinerary") {
                item {
                    DayHorizontalScrollItem(itinerary.daysMap.values.toList(), currentSelectedDate)
                }
                item {
                    LazyColumn(
                        modifier = Modifier.height(800.dp),
                        userScrollEnabled = isScrollingBlocked
                    ) {
                        if(currentSelectedDate.value.travelEvents.isNotEmpty()) {
                            items(currentSelectedDate.value.travelEvents) {
                                TravelEventItem(travelEventEntity = it, navController)
                            }
                        } else {
                            item {
                                Text(text = "No events",
                                    modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            } else if (selectedItem == "explore") {
                item {
                    Text(text = "Explore")
                }
            }
        }
        if (!isTopBarVisible) {
            TimelineItineraryContent(itineraryEntity = itinerary, contentHeight = currentImgSize, imgAlpha = currentImgAlpha)
        } else {
            TopBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Mumbai", style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(start = 30.dp)) },
        navigationIcon = {
            IconButton(onClick = { /* Handle back button click */ }) {
                Icon(painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp))
            }
        },
        actions = {
            IconButton(onClick = { /* Handle action */ }) {
                Icon(painter = painterResource(id = R.drawable.map_outline_icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp))
            }
        },
    )
}


val travelEvents = listOf(
    TravelEventEntity(
        id = 1,
        title = "Mumbai, Maharashtra",
        startTimestamp = 1,
        endTimestamp = 13241423143,
        imagesUrl = listOf(
            "https://www.holidify.com/images/cmsuploads/compressed/shutterstock_1073721995_20191213105915_20191213105938.jpg"
        ),
        latitude = 18.910000,
        longitude = 72.809998,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 1,
        title = "The GateWay of India",
        startTimestamp = 1,
        endTimestamp = 999999999999999999,
        imagesUrl = listOf("https://media.istockphoto.com/id/1154094384/photo/monsoon-clouds-over-the-india-gate.jpg?s=1024x1024&w=is&k=20&c=0gwnoZ4qUc-XS81Cf-ACaKVxnBCrjvnddMSpCcxvWKI="),
        latitude = 18.910000,
        longitude = 72.809998,
        tags = listOf("Review", "Review", "Restaurant", "Review", "Review", "Review"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 2,
        title = "The GateWay of India",
        startTimestamp = 1,
        endTimestamp = 1,
        imagesUrl = listOf("https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls="
        ),
        latitude = 18.910000,
        longitude = 72.809998,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 3,
        title = "The GateWay of India",
        startTimestamp = 1,
        endTimestamp = 1,
        imagesUrl = listOf("https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls="
        ),
        latitude = 18.910000,
        longitude = 72.809998,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 4,
        title = "The GateWay of India",
        startTimestamp = 1,
        endTimestamp = 1,
        imagesUrl = listOf("https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls="
        ),
        latitude = 18.910000,
        longitude = 72.809998,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    )
)

@SuppressLint("NewApi")
val days = listOf(
    DayTimeLineEntity(1, LocalDate.of(2023, 1, 1), 1, travelEvents),
    DayTimeLineEntity(2, LocalDate.of(2023, 1, 2), 1, listOf()),
    DayTimeLineEntity(3, LocalDate.of(2023, 1, 3), 1, listOf()),
    DayTimeLineEntity(4, LocalDate.of(2023, 1, 4), 1, listOf()),
    DayTimeLineEntity(5, LocalDate.of(2023, 1, 5), 1, listOf())
)

@SuppressLint("NewApi")
val itinerary = ItineraryEntity("Mumbai",
    "Maharashtra",
    "India",
    LocalDate.of(2023, 1, 1),
    LocalDate.of(2023, 1, 5),
    generateMap(getDatesBetween(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5)), days)
)

fun generateMap(listOfDate: List<LocalDate>, listOfEvents: List<DayTimeLineEntity>): Map<LocalDate, DayTimeLineEntity> {
   return listOfDate.associateWith { date ->
        listOfEvents.find { it.day == date } ?: DayTimeLineEntity(0, date, 0, listOf())
    }
}
// generates a list of dates from start and end date
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
    return startDate.datesUntil(endDate.plusDays(1)) // Adding 1 day to include the end date
        .toList()
}
