package com.myprojects.flickssaga.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.User
import com.myprojects.flickssaga.ui.screens.userList
import com.myprojects.flickssaga.ui.theme.Typography
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HideFromBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
            scope.launch {
                sheetState.hide()  // Collapse the sheet safely
            }
        },
        sheetState = sheetState
    ) {
        BottomSheetContent(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            onDoneClick = {
                showBottomSheet.value = false
                scope.launch {
                    sheetState.hide()  // Collapse the sheet safely
                }
            }
        )
    }
}


@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val selected = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Hide me from",
                style = Typography.bodyMedium.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            SearchBar(username)
            // Use a weight modifier to make the grid scrollable and take up available space
            Box(modifier = Modifier) {
                UserGridView(listOfUsers = listOfUsers, username = username, selected =  selected)
            }
        }

        // Static Done button at the bottom
        Button(
            onClick = { onDoneClick() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 6.dp)
                .fillMaxWidth()
                .padding(vertical = 16.dp), // Adjust padding as needed
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff00A3FF)
            )
        ) {
            Text(
                text = "Done",
                style = Typography.bodyMedium.copy(
                    fontSize = 15.sp
                ),
            )
        }
    }
}

@Composable
fun SearchBar(
    username: MutableState<String>,
) {
    OutlinedTextField(
        value = username.value,
        onValueChange = {
            username.value = it
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        },
        placeholder = {
            Text(
                text = "Search your friend",
                style = Typography.bodyMedium.copy(
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun UserGridView(
    listOfUsers: List<User>,
    username: MutableState<String>,
    selected: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    // Filter the list based on the search input
    val filteredUsers = if (username.value.isEmpty()) {
        listOfUsers
    } else {
        listOfUsers.filter { user ->
            user.username.contains(username.value, ignoreCase = true)
        }
    }

    // LazyVerticalGrid will update dynamically as the filteredUsers list changes
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // 4 columns in the grid
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Adjust height as necessary
            .padding(top = 8.dp, bottom = 16.dp),
        contentPadding = PaddingValues(top = 8.dp),
    ) {
        items(filteredUsers.size) { index ->
            UserItem(user = filteredUsers[index], selected)
        }
    }
}


@Composable
fun UserItem(user: User, selected: MutableState<Boolean>) {
    selected.value = user.isSelected

    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                selected.value = !selected.value
                user.isSelected = !user.isSelected
            }
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = if(selected.value)
                Modifier.border(
                    2.dp,
                    Color(0xff00A3FF),
                    CircleShape
                )
            else
                Modifier
        ) {
            AsyncImage(
                model = "https://plus.unsplash.com/premium_photo-1669882305273-674eff6567af?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fHVzZXJ8ZW58MHx8MHx8fDA%3D",
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            if(selected.value) {
                Icon(
                    painter = painterResource(id = R.drawable.tick_fill),
                    contentDescription = "",
                    tint = Color(0xff00a3ff),
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        Text(
            text = user.username,
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


val listOfUsers = listOf(
    User(
        id = 1,
        image = R.drawable.user,
        username = "abc",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "bcd",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "efg",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "hij",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "klm",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "nop",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "qrs",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "uvw",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "xyz",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "safd",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "ahfdbc",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "ewyr",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "asdfa",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "xcv",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "bcvcb",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "ahfdbc",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "ewyr",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "asdfa",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "xcv",
        email = "charleswfairbanks@myownpersonaldomain.com"
    ),
    User(
        id = 1,
        image = R.drawable.user,
        username = "bcvcb",
        email = "charleswfairbanks@myownpersonaldomain.com"
    )
)