package com.myprojects.flickssaga.ui.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.AppIconShare
import com.myprojects.flickssaga.data.User
import com.myprojects.flickssaga.ui.components.BackPressHandler
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.components.CustomCircularProgressBar
import com.myprojects.flickssaga.ui.components.Drawer
import com.myprojects.flickssaga.ui.components.DrawerItem
import com.myprojects.flickssaga.ui.components.DrawerItems
import com.myprojects.flickssaga.ui.components.PostContent
import com.myprojects.flickssaga.ui.components.Screen
import com.myprojects.flickssaga.ui.components.TopBar
import com.myprojects.flickssaga.ui.components.UploadPost
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import com.myprojects.flickssaga.viewmodels.VideoPostViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val drawerState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showBottomSheet = remember { mutableStateOf(false) }

    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
    )

    val videoPostViewModel = VideoPostViewModel()

    val scrollState = rememberScrollState()

    var username = remember { mutableStateOf("") }

    val scaffoldOffset by animateDpAsState(
        targetValue = if (drawerState.value) 40.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val verticalPadding by animateDpAsState(
        targetValue = if (drawerState.value) 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (drawerState.value) 16.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val topBar: @Composable () -> Unit = {
        TopBar(
            title = "Feed",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = {
                scope.launch {
                    drawerState.value = !drawerState.value
                }
            }
        )
    }

    Row(
        modifier = Modifier.background(Color.Black)
    ) {
        if (drawerState.value) {
            AnimatedVisibility(
                visible = drawerState.value,
                enter = slideInHorizontally(animationSpec = spring(stiffness = Spring.StiffnessLow), initialOffsetX = { -220 }) + fadeIn() + scaleIn(initialScale = 0.9f),
                exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessLow), targetOffsetX = { -220 }) + fadeOut() + scaleOut(targetScale = 0.9f)
            ) {
                Drawer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(220.dp)
                        .background(Color.Black),
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.value = false
                            navHostController.navigate(route)
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = scaffoldOffset.roundToPx(),
                        y = 0
                    )
                }
                .padding(top = verticalPadding, bottom = verticalPadding)
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colors.surface)
        ) {
            Scaffold(
                topBar = { topBar() },
                bottomBar = { BottomNavigationBar(navController = navHostController) },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = {},
                        icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                        onClick = { showBottomSheet.value = true },
                        backgroundColor = Color.White,
                        modifier = Modifier.padding(bottom = 50.dp)
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + scaffoldOffset,
                            top = innerPadding.calculateTopPadding() + verticalPadding,
                            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                            bottom = innerPadding.calculateBottomPadding() + verticalPadding
                        )
                ) {
                    // Screen content

                    LaunchedEffect(Unit) {
                        progress = 1f // Set progress to 100%
                    }

                    PostScreen(videoPostViewModel = videoPostViewModel)
                    if (showBottomSheet.value) {

                        // Share bottom Sheet UI
//                        ModalBottomSheet(
//                            onDismissRequest = { showBottomSheet = false },
//                            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//                            modifier = Modifier.fillMaxHeight(0.65f),
//                        ) {
//                            BottomSheetContent(username)
//                        }


                        UploadPost(videoPostViewModel = videoPostViewModel, showBottomSheet = showBottomSheet)
                    }
                }
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    username: MutableState<String>
) {

    val selected = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Share post",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 20.sp
                ),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.link),
                    contentDescription = "share link",
                    Modifier.size(25.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icons8_search),
                contentDescription = "Search",
                Modifier
                    .size(26.dp)
                    .padding(end = 8.dp),
                tint = Color.Gray
            )
            BasicTextField(
                value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .padding(horizontal = 2.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (username.value.isEmpty()) {
                            Text(
                                text = "username",
                                style = TextStyle(
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(

                )
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "via Vasukam",
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            ),
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            items(userList.size) {
                if(username.value.isEmpty()) {
                    UserItem(user = userList[it], selected)
                    if (it < userList.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp)) // Added some width to spacer
                    }
                }else if (userList[it].username.contains(username.value)) {
                    UserItem(user = userList[it], selected)
                    if (it < userList.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp)) // Added some width to spacer
                    }
                }
            }
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "via",
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            ),
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(AppIcons.size) {
                IconItem(appIcon = AppIcons[it])
                if (it < AppIcons.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp)) // Added some width to spacer
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, selected: MutableState<Boolean>) {
    selected.value = user.isSelected

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), // Ensure no padding here
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = user.image),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = user.username,
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            ),
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = user.email,
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            ),
            color = Color.White,
            fontWeight = FontWeight.Normal
        )

        val background = if (!selected.value) Color(0xFF007BFF) else Color.White
        val contentColor = if (!selected.value) Color.White else Color(0xFF007BFF)
        Button(
            onClick = {
                selected.value = !selected.value
                user.isSelected = !user.isSelected
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .widthIn(min = 90.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = background,
                contentColor = contentColor
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = if (!selected.value) "Send" else "Undo",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 12.sp
                ),
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun IconItem(appIcon: AppIconShare) {
    val context = LocalContext.current
    val pm: PackageManager = context.packageManager

    Column(
        modifier = Modifier.padding(8.dp), // Ensure no padding here
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp) // Size of the circle
                .background(Color.Black, CircleShape) // Black circle background
                .padding(16.dp), // Padding inside the circle
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                try {

                var launchIntent: Intent? = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain" // Use appropriate MIME type
                    putExtra(Intent.EXTRA_STREAM, appIcon.link)
                    setPackage(appIcon.packageManager)
                }

                    val apps = pm.queryIntentActivities(launchIntent!!, PackageManager.MATCH_DEFAULT_ONLY)
                    val isAppInstalled = apps.any { it.activityInfo.packageName == appIcon.packageManager}

                    if(appIcon.packageManager == "com.whatsapp") {
                        val uri = Uri.parse("https://wa.me/?text=${Uri.encode(appIcon.link)}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }else if (isAppInstalled) {
                        context.startActivity(launchIntent)
                    } else {
                        // App is not installed, redirect to Play Store
                        val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${appIcon.packageManager}"))
                        context.startActivity(playStoreIntent)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            } }
            ) {
                Icon(
                    painter = painterResource(id = appIcon.icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun UserItemPreview() {
//    val user = User(1, R.drawable.user, "abc", "@emailId")
//    UserItem(user)
//}

@Preview
@Composable
fun BottomSheetContentPreview() {
    val username: MutableState<String> = remember {
        mutableStateOf("")
    }
    BottomSheetContent(username)
}


val userList: List<User> = listOf(
    User(1, R.drawable.user, "abc", "@emailId"),
    User(1, R.drawable.user, "def", "@emailId"),
    User(1, R.drawable.user, "ghi", "@emailId"),
    User(1, R.drawable.user, "jkl", "@emailId"),
    User(1, R.drawable.user, "mno", "@emailId"),
    User(1, R.drawable.user, "pqr", "@emailId"),
    User(1, R.drawable.user, "sau", "@emailId"),
    User(1, R.drawable.user, "vwx", "@emailId"),
    User(1, R.drawable.user, "yz", "@emailId"),
)

val AppIcons: List<AppIconShare> = listOf(
    AppIconShare(R.drawable.instagram, "com.instagram.android", "https://bond.ownsfare.com/win/pjJTBc5tKQWLvVUa8"),
    AppIconShare(R.drawable.linkedin, "com.linkedin.android", "https://bond.ownsfare.com/win/pjJTBc5tKQWLvVUa8"),
    AppIconShare(R.drawable.whatsapp, "com.whatsapp", "https://bond.ownsfare.com/win/pjJTBc5tKQWLvVUa8"),
    AppIconShare(R.drawable.icons8_twitterx, "com.twitter.android", "https://bond.ownsfare.com/win/pjJTBc5tKQWLvVUa8"),
    AppIconShare(R.drawable.more, "null", "https://bond.ownsfare.com/win/pjJTBc5tKQWLvVUa8"),
)

