package com.myprojects.flickssaga

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.screens.map.DiscoverScreen
import com.myprojects.flickssaga.ui.components.DrawerItems
import com.myprojects.flickssaga.ui.components.LocalBackPressedDispatcher
import com.myprojects.flickssaga.ui.components.Screen
import com.myprojects.flickssaga.ui.screens.ChatScreen
import com.myprojects.flickssaga.ui.screens.FlicksScreen
import com.myprojects.flickssaga.ui.screens.HomeScreen
import com.myprojects.flickssaga.ui.screens.UploadScreen
import com.myprojects.flickssaga.ui.screens.notifications.NotificationScreen
import com.myprojects.flickssaga.viewmodels.FlickViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: MainActivity
) {
    val flickViewModel = FlickViewModel(FlickRepository())
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            CompositionLocalProvider(LocalBackPressedDispatcher provides context.onBackPressedDispatcher) {
                HomeScreen(navController)
            }
        }
        composable(Screen.Upload.route) { UploadScreen(flickViewModel, navController) }
        composable(Screen.Flicks.route) { FlicksScreen(flickViewModel) }
        composable(DrawerItems.Questionnaire.route) { ChatScreen(navController) }
        composable(DrawerItems.YourActivity.route) { NotificationScreen() }
        composable(Screen.Discover.route) { DiscoverScreen(navController) }
    }
}