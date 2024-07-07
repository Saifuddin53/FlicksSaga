package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.User
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val sheetState =
        androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var username = remember {
        mutableStateOf("")
    }
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(text = {},
            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
            onClick = {
                showBottomSheet = true
            },
            backgroundColor = Color.White,
            modifier = Modifier.padding(bottom = 50.dp)
        )
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding))
        // Screen content

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                modifier = Modifier.fillMaxHeight(0.65f),
            ) {
                BottomSheetContent(
                    username
                )
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
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Share post", style = TextStyle(
                    fontFamily = poppinsFontFamily, fontSize = 20.sp
                ), color = Color.White, fontWeight = FontWeight.Bold
            )
            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent, contentColor = Color.White
                ), elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp, pressedElevation = 0.dp
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
                .padding(start = 16.dp, top = 0.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icons8_search),
                contentDescription = "Search",
                Modifier
                    .size(26.dp)
                    .padding(bottom = 0.dp, top = 7.dp),
                tint = Color.Gray
            )
            BasicTextField(value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 5.dp),
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
                                text = "username", style = TextStyle(
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text = "via Vasukam", style = TextStyle(
                fontFamily = poppinsFontFamily, fontSize = 14.sp
            ), color = Color.Gray, modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 16.dp)
        ) {
            items(userList.size) {
                if(username.value.isEmpty()) {
                    UserItem(user = userList[it], selected)
                    if (it < userList.size - 1) {
                        Spacer(modifier = Modifier.width(0.dp)) // Spacer with no width, can adjust if needed
                    }
                }else if (userList[it].username.contains(username.value)) {
                    UserItem(user = userList[it], selected)
                    if (it < userList.size - 1) {
                        Spacer(modifier = Modifier.width(0.dp)) // Spacer with no width, can adjust if needed
                    }
                }

            }
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )
        Text(
            text = "via", style = TextStyle(
                fontFamily = poppinsFontFamily, fontSize = 14.sp
            ), color = Color.Gray, modifier = Modifier.padding(start = 16.dp, top = 12.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 16.dp)
        ) {
            items(iconIDs.size) {
                IconItem(icon = iconIDs[it])
                if (it < userList.size - 1) {
                    Spacer(modifier = Modifier.width(0.dp)) // Spacer with no width, can adjust if needed
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, selected: MutableState<Boolean> ) {
    selected.value = user.isSelected

    Column(
        modifier = Modifier.padding(start = 16.dp), // Ensure no padding here
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
                fontFamily = poppinsFontFamily, fontSize = 14.sp
            ),
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = user.email, style = TextStyle(
                fontFamily = poppinsFontFamily, fontSize = 14.sp
            ), color = Color.White, fontWeight = FontWeight.Normal
        )


        val background = if(!selected.value) Color(0xFF007BFF) else Color.White
        val contentColor = if(!selected.value) Color.White else Color(0xFF007BFF)
        Button(
            onClick = { selected.value = !selected.value
                      user.isSelected = !user.isSelected},
            modifier = Modifier
                .padding(vertical = 4.dp)
                .widthIn(min = 90.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = background, contentColor = contentColor
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = if(!selected.value) "Send" else "Undo", style = TextStyle(
                    fontFamily = poppinsFontFamily, fontSize = 12.sp
                ), fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun IconItem(icon: Int) {
    Column(
        modifier = Modifier.padding(start = 16.dp), // Ensure no padding here
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp) // Size of the circle
                .background(Color.Black, CircleShape) // Black circle background
                .padding(16.dp), // Padding inside the circle
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
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

val iconIDs: List<Int> = listOf(
    R.drawable.instagram,
    R.drawable.linkedin,
    R.drawable.whatsapp,
    R.drawable.icons8_twitterx,
    R.drawable.more
)