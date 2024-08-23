package com.myprojects.flickssaga.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

//
//@Composable
//fun BottomNavigationBar(navController: NavHostController) {
//    val items = listOf(
//        Screen.Home,
//        Screen.Upload,
//        Screen.Flicks
//    )
//    BottomNavigation(
//        backgroundColor = MaterialTheme.colors.primary,
//        contentColor = Color.White
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        items.forEach { screen ->
//            BottomNavigationItem(
//                icon = {
//                    Icon(
//                        painter = painterResource(id = screen.icon),
//                        contentDescription = screen.route
//                    )
//                },
//                label = { Text(screen.title) },
//                selected = currentRoute == screen.route,
//                onClick = {
//                    if (currentRoute != screen.route) {
//                        navController.navigate(screen.route) {
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                }
//            )
//        }
//    }
//}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Discover,
        Screen.Upload,
        Screen.Flicks,
        Screen.Marketplace
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Gray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.route,
                        Modifier.size(22.dp)
                    )
                },
                label = { Text(text = screen.title,
                    style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                )
                ) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = true,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = false
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}


sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Discover : Screen("discover", "Discover", R.drawable.discover)
    object Upload : Screen("upload", "Upload", R.drawable.ic_upload)
    object Marketplace : Screen("marketplace", "Market", R.drawable.shop)
    object Flicks : Screen("flicks", "Flicks", R.drawable.ic_flicks)
}