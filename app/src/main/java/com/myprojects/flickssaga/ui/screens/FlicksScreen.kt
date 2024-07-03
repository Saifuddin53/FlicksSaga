package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.components.VideoPlayer
import com.myprojects.flickssaga.viewmodels.FlickViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlicksScreen(
    viewModel: FlickViewModel,
    modifier: Modifier = Modifier
) {

    val flicks by viewModel.flicks.collectAsState()
    val currentFlick by viewModel.currentFlicks.collectAsState()

    val pagerState = rememberPagerState() {
        flicks.size
    }

    val isFirstItem by remember(pagerState) {
        derivedStateOf {
            pagerState.currentPage == 0
        }
    }


}

@Composable
fun FlickItem(
    flick: Flick
) {
   Box(
       modifier = Modifier
           .fillMaxSize()
           .verticalScroll(rememberScrollState())
   ) {
       VideoPlayer(videoUrl = flick.videoUrl)
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Bottom
       ) {
           Text(text = flick.title)
           Text(text = flick.description)
       }
   }
}

@Composable
fun Check(
    viewModel: FlickViewModel,
    modifier: Modifier = Modifier
) {
    val currentFlick by viewModel.currentFlicks.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        VideoPlayer(videoUrl = "https://user-images.githubusercontent.com/90382113/170887700-e405c71e-fe31-458d-8572-aea2e801eecc.mp4")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = "")
            Text(text = "")
        }
    }
}

//@Preview
//@Composable
//fun FlicksScreenPreview() {
//    FlicksScreen()
//}