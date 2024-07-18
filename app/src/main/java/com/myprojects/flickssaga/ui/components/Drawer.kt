package com.myprojects.flickssaga.ui.components


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.theme.FlicksSagaTheme
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
            .padding(bottom = 60.dp)
    ) {
        Column(
            modifier = modifier
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Column(modifier = Modifier) {
                    Text(
                        text = "Aakash Shukla",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 16.sp
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        text = "View Profile",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp
                        ),
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp, start = 10.dp)
                    )

                }
            }


            DrawerItem(DrawerItems.Incognito)
            DrawerItem(DrawerItems.Abilities)
            DrawerItem(DrawerItems.Analytics)
            DrawerItem(DrawerItems.YourActivity)
            DrawerItem(DrawerItems.SavedPosts)
            DrawerItem(DrawerItems.YourGroups)
            DrawerItem(DrawerItems.CloseFriendsList)
            DrawerItem(DrawerItems.Questionnaire, onDestinationClicked = onDestinationClicked)
            DrawerItem(DrawerItems.YourEShop)
            DrawerItem(DrawerItems.Approvals)
            DrawerItem(DrawerItems.SendReal)
            DrawerItem(DrawerItems.Wallet)
            DrawerItem(DrawerItems.Invite)
            DrawerItem(drawerItems = DrawerItems.Settings)
            DrawerItem(drawerItems = DrawerItems.LogOut, color = Color.Red)
        }

    }
}

@Composable
fun DrawerItem(drawerItems: DrawerItems, color: Color = Color.White, onDestinationClicked: (route: String) -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
            .clickable(onClick = { })
            .height(40.dp)
            .background(color = Color.Transparent)
            .clickable {
                onDestinationClicked(drawerItems.route)
            }
    ) {
        Icon(
            painter = painterResource(id = drawerItems.icon),
            contentDescription = drawerItems.title,
            tint = color,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
                .padding(start = 8.dp)
        )
        Text(
            text = drawerItems.title,
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp
            ),
            color = color,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Preview
@Composable
fun DrawerPreview() {
    FlicksSagaTheme{
        Drawer {}
    }
}


sealed class DrawerItems(
    val route: String,
    val icon: Int,
    val title: String
) {
    object Incognito : DrawerItems("home", R.drawable.smileycross, "Incognito")
    object Abilities : DrawerItems("profile", R.drawable.atom, "Abilities")
    object Analytics : DrawerItems("settings", R.drawable.chartlines, "Analytics")
    object YourActivity : DrawerItems("help", R.drawable.timer, "Your activity")
    object SavedPosts : DrawerItems("logout", R.drawable.bookmarksimple, "Saved Posts")
    object YourGroups : DrawerItems("about", R.drawable.usersthree, "Your Groups")
    object CloseFriendsList : DrawerItems("feedback", R.drawable.smiley, "Close Friends List")
    object Questionnaire : DrawerItems("chat", R.drawable.chatteardrop, "Questionnaire")
    object YourEShop : DrawerItems("rate", R.drawable.storefront, "Your eShop")
    object Approvals : DrawerItems("more", R.drawable.checkcircle, "Approvals")
    object SendReal : DrawerItems("contact", R.drawable.currencydollarsimple, "SendReal")
    object Wallet : DrawerItems("privacy", R.drawable.wallet, "Wallet")
    object Invite : DrawerItems("terms", R.drawable.sharenetwork, "Invite")
    object Settings : DrawerItems("logout", R.drawable.gear, "Settings")
    object LogOut : DrawerItems("logout", R.drawable.signout, "Log Out")
}

