package com.myprojects.flickssaga.ui.screens.map.map_impl

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.screens.map.map_impl.components.MapMultipleMarker
import com.myprojects.flickssaga.ui.screens.map.map_impl.components.PersonDistanceCard
import com.myprojects.flickssaga.ui.screens.map.models.LocationDetails
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.models.UserProfile
import com.myprojects.flickssaga.ui.screens.map.ui_components.CardTravelEventItem
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.background(color = Color.White),
        bottomBar = { BottomNavigationBar(navController = navController)}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MapMultipleMarker(
                modifier = Modifier.padding(innerPadding),
                onMarkerClick = { travelEventId ->
                    // Scroll to the item corresponding to the travelEventId
                    val index = travelEventsMap.indexOfFirst { it.id == travelEventId }
                    if (index != -1) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                        }
                    }
                }
            )

            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                items(userProfiles) { user ->
                    PersonDistanceCard(
                        user
                    )
                }
            }
        }
    }
}

val travelEventsMap = listOf(
    TravelEventEntity(
        id = 1,
        title = "Teen darwaja",
        startTimestamp = 1,
        endTimestamp = 13241423143,
        imagesUrl = listOf(
            "https://lh3.googleusercontent.com/p/AF1QipO6GuHgXih6UM2RjEgjeC8-fQDSWEVh8l0NM3wM=s1360-w1360-h1020"
        ),
        latitude = 23.0154404,
        longitude = 72.5767402,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 2,
        title = "Kankaria lake",
        startTimestamp = 1,
        endTimestamp = 999999999999999999,
        imagesUrl = listOf("https://lh5.googleusercontent.com/p/AF1QipNxNPxauDIt4WSQ3O2grRgZ2zWL1Ayv8dW9CVvH=w408-h272-k-no"),
        latitude = 22.9786,
        longitude = 72.6031,
        tags = listOf("Review", "Review", "Restaurant", "Review", "Review", "Review"),
        distance = "10m"
    ),
    TravelEventEntity(
        id = 3,
        title = "Manek Chowk",
        startTimestamp = 1,
        endTimestamp = 1,
        imagesUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Manekchok.jpg/330px-Manekchok.jpg"
        ),
        latitude = 23.0271,
        longitude = 72.5895,
        tags = listOf("Review", "Review", "Restaurant"),
        distance = "10m"
    ),

)

val userProfiles = listOf(
    UserProfile(
        name = "Aniket",
        username = "@aniket",
        profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
        address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
        locationDetails = LocationDetails(
            locationName = "Ji.Raya Yeh Gangga",
            distance = "200m",
            travelTime = "5 min"
        ),
        sharedConnection = "Jay Rajput and you is the same group"
    ),
    UserProfile(
        name = "Aniket",
        username = "@aniket",
        profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
        address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
        locationDetails = LocationDetails(
            locationName = "Ji.Raya Yeh Gangga",
            distance = "200m",
            travelTime = "5 min"
        )
    ),
    UserProfile(
        name = "Aniket",
        username = "@aniket",
        profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
        address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
        locationDetails = LocationDetails(
            locationName = "Ji.Raya Yeh Gangga",
            distance = "200m",
            travelTime = "5 min"
        )
    ),
    UserProfile(
        name = "Aniket",
        username = "@aniket",
        profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
        address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
        locationDetails = LocationDetails(
            locationName = "Ji.Raya Yeh Gangga",
            distance = "200m",
            travelTime = "5 min"
        )
    ),
)