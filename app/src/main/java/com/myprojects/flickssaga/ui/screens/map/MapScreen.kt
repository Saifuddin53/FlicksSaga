package com.myprojects.flickssaga.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Itinerary") },
                navigationIcon = {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                },
                actions = { /* Any action buttons */ }
            )
        },
        floatingActionButton = {
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { padding ->
        TimelineScreen(modifier = Modifier.padding(padding))
    }
}

@Composable
fun TimelineScreen(modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            TimelineItem(
                time = "10:00 AM - 12:00 PM",
                title = "The Gateway of India",
                location = "Apollo Bandar, Colaba, Mumbai, Maharashtra",
            )
        }
        item {
            TimelineItem(
                time = "12:30 PM - 03:00 PM",
                title = "CSMT",
                location = "Chhatrapati Shivaji Maharaj Terminus, Mumbai",
            )
        }
        // Add more timeline items here
    }
}


@Composable
fun TimelineItem(
    time: String,
    title: String,
    location: String,
) {
    Column {
        Text(text = time,
            style = TextStyle(
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                AsyncImage(
                    model = "https://media.istockphoto.com/id/1307189136/photo/gateway-of-india-mumbai-maharashtra-monument-landmark-famous-place-magnificent-view-without.jpg?s=1024x1024&w=is&k=20&c=veWfug23i64lW0zEcxlX6tyRbrDIyDZYmF-GxqNOZls=",
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = location,
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { /* Handle click */ }) {
                        Text("Restaurants")
                    }
                    TextButton(onClick = { /* Handle click */ }) {
                        Text("Cafe")
                    }
                    TextButton(onClick = { /* Handle click */ }) {
                        Text("Photo Spot")
                    }
                }
            }
        }
    }
}
