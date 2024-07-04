@file:JvmName("FlicksScreenKt")

package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.components.Pager
import com.myprojects.flickssaga.ui.components.PagerState
import com.myprojects.flickssaga.ui.components.VideoPlayer
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.delay
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.FlickState

@Composable
fun FlicksScreen() {
    val viewModel = FlickViewModel(FlickRepository())
    FlicksScreen(viewModel = viewModel)
}

@Composable
fun FlicksScreen(
    viewModel: FlickViewModel,
    clickItemPosition: Int = 0,
) {

    val flicks by viewModel.flicks.collectAsState()

    val flickStateMutable: MutableState<FlickState> = remember {
        mutableStateOf(FlickState.Ready)
    }
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

    when(flickStateMutable.value) {
        is FlickState.Idle -> {}
        is FlickState.Buffering -> {}
        is FlickState.Ready, FlickState.Ended -> {
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
                    flickStateMutable
                )
            }

            LaunchedEffect(clickItemPosition) {
                delay(300)
                initialLayout.value = false
            }
        }
        is FlickState.Error -> {}
        else -> {}
    }
}

@Composable
private fun SingleVideoItemContent(
    flick: Flick,
    pagerState: PagerState,
    pager: Int,
    initialLayout: MutableState<Boolean>,
    pauseIconVisibleState: MutableState<Boolean>,
    flickState: MutableState<FlickState>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(flick.videoUrl, pagerState, pager, pauseIconVisibleState, flickState)
        FlickHeader(flick)
        Box(modifier = Modifier.align(Alignment.BottomStart)) {
        }
        if (initialLayout.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            )
        }

        if(flickState.value == FlickState.Ended) {
            NextScreenQuestion()
        }
    }
}


@Composable
fun FlickHeader(flick: Flick) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(vertical = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = flick.title,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 15.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_outlined_camera),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 15.dp)
                .size(28.dp)
        )
    }
}






