package com.myprojects.flickssaga

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.components.Screen
import com.myprojects.flickssaga.ui.screens.FlicksScreen
import com.myprojects.flickssaga.ui.screens.HomeScreen
import com.myprojects.flickssaga.ui.screens.UploadScreen
import com.myprojects.flickssaga.viewmodels.FlickViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val flickViewModel = FlickViewModel(FlickRepository())
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Upload.route) { UploadScreen(flickViewModel) }
        composable(Screen.Flicks.route) { FlicksScreen(flickViewModel) }
    }
}