package com.myprojects.flickssaga

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import androidx.transition.TransitionValues
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.components.DrawerItems
import com.myprojects.flickssaga.ui.components.LocalBackPressedDispatcher
import com.myprojects.flickssaga.ui.screens.ChatScreen
import com.myprojects.flickssaga.ui.screens.FlicksScreen
import com.myprojects.flickssaga.ui.screens.HomeScreen
import com.myprojects.flickssaga.ui.screens.UploadScreen
import com.myprojects.flickssaga.ui.screens.map.DiscoverScreen
import com.myprojects.flickssaga.ui.screens.map.TravelEventScreen
import com.myprojects.flickssaga.ui.screens.map.map_impl.MapScreen
import com.myprojects.flickssaga.ui.screens.map.models.TravelEventEntity
import com.myprojects.flickssaga.ui.screens.map.trip.TripScreen
import com.myprojects.flickssaga.ui.screens.notifications.NotificationScreen
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: MainActivity
) {
    val flickViewModel = FlickViewModel(FlickRepository())
    SharedTransitionLayout {
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
            composable(Screen.Discover.route) {
                DiscoverScreen(navController)
//                MapScreen(navController)
//                TripScreen(navController)
            }
            composable<DetailItem> {
                val detailItem = it.toRoute<DetailItem>()
                TravelEventScreen(
                    id = detailItem.id,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}

@Serializable
data class DetailItem(
    val id: Int
)

sealed class Screen(val route: String, val title: String, val icon: Int = 0) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Discover : Screen("discover", "Discover", R.drawable.discover)
    object Upload : Screen("upload", "Upload", R.drawable.ic_upload)
    object Marketplace : Screen("marketplace", "Market", R.drawable.shop)
    object Flicks : Screen("flicks", "Flicks", R.drawable.ic_flicks)
}