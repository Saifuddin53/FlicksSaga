package com.myprojects.flickssaga.ui.components

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import com.myprojects.flickssaga.data.FlickState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    pagerState: PagerState,
    pager: Int,
    pauseIconVisibleState: MutableState<Boolean>,
    flickState: MutableState<FlickState>
) {
    val context = LocalContext.current
    val scope= rememberCoroutineScope()

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(videoUrl)
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)

                this.prepare(source)
            }
    }

    if (pager == pagerState.currentPage) {
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    } else {
        exoPlayer.pause()
    }

    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_OFF

    exoPlayer.addListener(object: Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED) {
                flickState.value = FlickState.Ended
            }
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(factory = {
        androidx.media3.ui.PlayerView(context).apply {
            hideController()
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player = exoPlayer
            layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                MATCH_PARENT
            )
        }
    },modifier = Modifier.noRippleClickable {
        pauseIconVisibleState.value=true
        exoPlayer.pause()
        scope.launch {
            delay(500)
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                pauseIconVisibleState.value=false
                exoPlayer.play()
            }
        }
    })
}
