package com.myprojects.flickssaga.ui.screens

import android.app.Activity
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.ui.components.Pager
import com.myprojects.flickssaga.ui.components.PagerState
import com.myprojects.flickssaga.ui.components.VideoPlayer
import com.myprojects.flickssaga.ui.components.immersive
import com.myprojects.flickssaga.ui.components.noRippleClickable
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShortViewCompose(
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







