package com.myprojects.flickssaga.ui.screens.map.map_impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.screens.map.map_impl.components.MapMultipleMarker
import com.myprojects.flickssaga.ui.screens.map.map_impl.components.MapSimple

@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.background(color = Color.White),
        bottomBar = { BottomNavigationBar(navController = navController)}
    ) { innerPadding ->
        MapMultipleMarker(modifier = Modifier.padding(innerPadding))
    }
}