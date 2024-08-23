package com.myprojects.flickssaga.ui.screens.map

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.components.TopBar
import com.myprojects.flickssaga.ui.screens.map.models.DayTimeLineEntity
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.ui_components.DayHorizontalScrollItem
import com.myprojects.flickssaga.ui.screens.map.ui_components.DayHorizontalScrollItemPreview
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
        TimelineScreen(modifier = Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineScreen(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf("itinerary") }
    var currentImgSize by remember { mutableStateOf(300f) }
    var currentImgAlpha by remember { mutableStateOf(1f) }
    var isTopBarVisible by remember { mutableStateOf(false) }
    var isScrollingBlocked by remember { mutableStateOf(false) }

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
                    DayHorizontalScrollItem(days)
                }
                item {
                    LazyColumn(
                        modifier = Modifier.height(800.dp),
                        userScrollEnabled = isScrollingBlocked
                    ) {
                        items(travelEvents) {
                            TravelEventItem(travelEvent = it)
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
            TimelineItineraryContent(contentHeight = currentImgSize, imgAlpha = currentImgAlpha)
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

@RequiresApi(Build.VERSION_CODES.O)
val days = listOf(
    DayTimeLineEntity(1, LocalDate.now(), 1, listOf()),
    DayTimeLineEntity(2, LocalDate.now(), 1, listOf()),
    DayTimeLineEntity(3, LocalDate.now(), 1, listOf()),
    DayTimeLineEntity(4, LocalDate.now(), 1, listOf()),
    DayTimeLineEntity(5, LocalDate.now(), 1, listOf())
)

val travelEvents = listOf(
    TravelEventEntity(
        id = 1,
        title = "The GateWay of India",
        startTimestamp = 1,
        endTimestamp = 999999999999999999,
        imagesUrl = listOf("https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
            "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls="
        ),
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