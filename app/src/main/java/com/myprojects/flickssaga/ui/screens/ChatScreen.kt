package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.ui.components.ImagesMessageContent
import com.myprojects.flickssaga.ui.components.PollContent
import com.myprojects.flickssaga.ui.components.PollDialog
import com.myprojects.flickssaga.ui.components.imagesList1
import com.myprojects.flickssaga.ui.components.imagesList2
import com.myprojects.flickssaga.ui.components.imagesList3
import com.myprojects.flickssaga.ui.components.imagesList4
import com.myprojects.flickssaga.ui.components.imagesList5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navHostController: NavHostController) {

    var showDialog by remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colors.primary,
                ),
                title = {
                    androidx.compose.material.Text(
                        "Chat",
                        fontSize = 22.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.previousBackStackEntry }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "")
                    }
                },
                actions = { },
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                ImagesMessageContent(imagesList1)
                ImagesMessageContent(imagesList2)
                ImagesMessageContent(imagesList3)
                ImagesMessageContent(imagesList4)
                ImagesMessageContent(imagesList5)
            }
        }
    }
}
