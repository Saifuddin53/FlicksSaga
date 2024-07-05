@file:JvmName("FlicksScreenKt")

package com.myprojects.flickssaga.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.myprojects.flickssaga.data.FlickState.Changed.currentFlick

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
    val context = LocalContext.current

    val flicks by viewModel.flicks.collectAsState()
    val currentFlick: MutableState<Flick> = remember {
        mutableStateOf(flicks.root!!)
    }

    val flickStateMutable: MutableState<FlickState> = remember {
        mutableStateOf(FlickState.Ready)
    }

    val initialLayout = remember {
        mutableStateOf(true)
    }

    when (flickStateMutable.value) {
        is FlickState.Idle -> {}
        is FlickState.Buffering -> {}
        is FlickState.Ready, FlickState.Ended -> {
            SingleVideoItemContent(
                currentFlick,
                flickStateMutable
            )

            LaunchedEffect(clickItemPosition) {
                delay(300)
                initialLayout.value = false
            }
        }

        is FlickState.Changed -> {
            currentFlick.value = FlickState.Changed.currentFlick!!
            flickStateMutable.value = FlickState.Ready
//            Toast.makeText(context, "Changed to ${currentFlick}", Toast.LENGTH_SHORT).show()
        }

        is FlickState.Error -> {}
        else -> {}
    }
}

@Composable
private fun SingleVideoItemContent(
    flick: MutableState<Flick>,
    flickState: MutableState<FlickState>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(flick.value.videoUrl, flickState)
        FlickHeader(flick.value)

        if (flickState.value == FlickState.Ended) {
            NextScreenQuestion(flick.value, flickState)
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
    ) {
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






