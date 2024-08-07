package com.myprojects.flickssaga.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.ui.components.PostContent
import com.myprojects.flickssaga.viewmodels.VideoPostViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(videoPostViewModel: VideoPostViewModel = viewModel()) {
    val posts by videoPostViewModel.posts.collectAsState()
    val listState = rememberLazyListState()
    val currentPostIndex by videoPostViewModel.currentPostIndex.collectAsState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index != currentPostIndex) {
                    videoPostViewModel.setCurrentPostIndex(index)
                }
            }
    }

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(posts.size) { index ->
            PostContent(post = posts[index], isCurrentlyVisible = index == currentPostIndex)
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}