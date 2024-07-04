@file:JvmName("FlicksScreenKt")

package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.components.Pager
import com.myprojects.flickssaga.ui.components.PagerState
import com.myprojects.flickssaga.ui.components.VideoPlayer
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.delay

@Composable
fun FlicksScreen() {
    val viewModel = FlickViewModel(FlickRepository())
    FlicksScreen(viewModel = viewModel)
}

@Composable
fun FlicksScreen(
    viewModel: FlickViewModel,
    clickItemPosition: Int = 0,
    videoHeader: @Composable () -> Unit = {},
    videoBottom: @Composable () -> Unit = {}
) {
    val flicks by viewModel.flicks.collectAsState()
    val pagerState: PagerState = run {
        remember {
            PagerState(clickItemPosition, 0, flicks.size - 1)
        }
    }
    val initialLayout = remember {
        mutableStateOf(true)
    }
    val pauseIconVisibleState = remember {
        mutableStateOf(false)
    }
    Pager(
        state = pagerState,
        orientation = Orientation.Vertical,
        offscreenLimit = 1
    ) {
        pauseIconVisibleState.value = false
        SingleVideoItemContent(
            flicks[page],
            pagerState,
            page,
            initialLayout,
            pauseIconVisibleState,
            videoHeader,
            videoBottom
        )
    }

    LaunchedEffect(clickItemPosition) {
        delay(300)
        initialLayout.value = false
    }

}

@Composable
private fun SingleVideoItemContent(
    flick: Flick,
    pagerState: PagerState,
    pager: Int,
    initialLayout: MutableState<Boolean>,
    pauseIconVisibleState: MutableState<Boolean>,
    VideoHeader: @Composable() () -> Unit,
    VideoBottom: @Composable() () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(flick.videoUrl, pagerState, pager, pauseIconVisibleState)
        VideoHeader.invoke()
        Box(modifier = Modifier.align(Alignment.BottomStart)) {
            VideoBottom.invoke()
        }
        if (initialLayout.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            )
        }
    }
}







