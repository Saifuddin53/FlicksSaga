package com.myprojects.flickssaga.ui.screens.map.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.CategoryItemData
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.FoodItemData
import com.myprojects.flickssaga.ui.screens.map.ui_components.StarRatingBar
import com.myprojects.flickssaga.ui.screens.map.ui_components.getAddressFromLocation
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun ExploreDetailScreen(
    title: String,
    navController: NavHostController
) {
    var category = cardListTopSearch.find { it.title == title }
    if(category == null) {
        category = cardList.find { it.title == title }
    }
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController)},
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
            ) {
                if (category != null) {
                    DetailItemTopSection(
                        category = category,
                        navController = navController
                    )
                }
                DetailScreenListContainer(
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun DetailItemTopSection(
    category: CategoryItemData,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
    ) {
        AsyncImage(
            model = category.image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 1f },
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.title,
                style =  Typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                ),
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    // TODO : HANDLE BACK NAVIGATION
                    navController.navigateUp()
                },
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = CircleShape
                    )
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 15.dp, end = 10.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun DetailScreenListContainer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .offset(y = -(10.dp))
    ) {
        Text(
            text = "Famous vadapav in mumbai",
            style =  Typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight(500),
                fontSize = 13.sp
            )
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 2.dp),
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(foodItemsList) { item ->
                DetailScreenFoodItem(foodItemData = item)
            }
        }
    }
}

@Composable
fun DetailScreenFoodItem(
    foodItemData: FoodItemData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.76f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = foodItemData.title,
                        style = Typography.bodyLarge.copy(
                            fontWeight = FontWeight(600),
                            fontSize = 14.sp,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        ),
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "4.5",
                            style = Typography.bodyMedium.copy(
                                fontSize = 9.sp,
                                fontWeight = FontWeight(500),
                                color = Color.White,
                            ),
                            modifier = Modifier
                                .padding(top = 2.dp, end = 6.dp)
                                .background(
                                    color = Color(0xff339D3A),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        )

                        StarRatingBar(
                            rating = 4.5f,
                            onRatingChanged = {},
                            size = 5f,
                            modifier = Modifier
                        )

                        Text(
                            text = "500 Rating",
                            style = Typography.bodyMedium.copy(
                                fontWeight = FontWeight(500),
                                color = Color(0xff595959),
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                    }
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xff595959),
                        )
                        Text(
                            text = getAddressFromLocation(LocalContext.current, foodItemData.locationLat, foodItemData.locationLong) ?: "",
                            style = Typography.bodyMedium.copy(
                                color = Color(0xff595959),
                                fontSize = 10.sp,
                                lineHeight = 14.sp
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp) // Adjust padding if needed
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .height(70.dp)
                        .padding(end = 8.dp, bottom = 6.dp)
                        .clickable { /* TODO */ }
                        .weight(0.24f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFffffff),
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    AsyncImage(
                        model = foodItemData.image,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${foodItemData.item}(${foodItemData.quantity} pc)",
                        style = Typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        ),
                    )
                    Text(
                        text = "\u20B9 ${foodItemData.price}",
                        style = Typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .border(1.dp, Color(0xff797979), shape = RoundedCornerShape(24.dp))
                        .clickable {
                            // TODO : HANDLE NAVIGATION TO DIRECTION SCREEN
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.direction),
                            contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            text = "Direction",
                            style = Typography.bodyMedium.copy(
                                fontWeight = FontWeight(500),
                                color = Color.Black,
                                fontSize = 10.sp
                            ),
                        )
                    }
                }
            }
        }
    }
}

val foodItemsList = listOf(
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    ),
    FoodItemData(
        title = "Thakur Vada pav",
        image = "https://thebombaycanteen.com/cdn/shop/files/TBC_BANNER_-_WEB.jpg?v=1722849058",
        ratings = 4.5,
        locationLat = 18.910000,
        locationLong = 72.809998,
        item = "Vadapav",
        price = 20,
        quantity = 1
    )
)