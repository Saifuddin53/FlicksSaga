package com.myprojects.flickssaga.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.UploadStates
import com.myprojects.flickssaga.ui.components.FlickDetailsModal
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun UploadScreen(
    flickViewModel: FlickViewModel,
) {
    val currentFlicks = flickViewModel.currentFlicks.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uploadStates by flickViewModel.uploadState.collectAsState()
    val thumbnail = remember { mutableStateOf(currentFlicks.value.thumbnailUrl) }
    val index = remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val rows = remember { mutableStateOf(1) }
    val horizontalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colors.primary,
                ),
                title = {
                    Text(
                        "Create Storyline",
                        fontSize = 22.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {},
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_preview),
                            contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .horizontalScroll(horizontalScrollState),
        ) {
            when (uploadStates) {
                is UploadStates.Idle -> {
                    index.value = 0
                    UploadScreenView(flickViewModel,index.value)
                }
                is UploadStates.Loading -> {
                    // Display loading state if necessary
                }
                is UploadStates.Success -> {
                    thumbnail.value = UploadStates.Success.newFlick?.thumbnailUrl
                    if (UploadStates.Success.newFlick?.rightFlick != null || UploadStates.Success.newFlick?.leftFlick != null) {
                        rows.value += 1
                    }
                    flickViewModel.resetUploadStateToIdle()
                }
                is UploadStates.Error -> {
                    // Handle error state if necessary
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UploadScreenView(
    viewModel: FlickViewModel,
    index:  Int,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        viewModel.flicks.value.root?.let {
            UploadScreenContent(it, viewModel, index)
        }
    }
}

@Composable
fun UploadScreenContent(
    flick: Flick,
    viewModel: FlickViewModel,
    index: Int
) {
    var rows: Int = 0

    val size = when(rows) {
        0 -> 1f
        1 -> 0.5f
        2 -> 0.25f
        3 -> 0.125f
        else -> 0f
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NodeContent(flick, viewModel, index)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 16.dp)
//                .fillMaxWidth(size)
        ) {
            flick.leftFlick?.let { UploadScreenContent(it, viewModel,it.id) }
            flick.rightFlick?.let { UploadScreenContent(it, viewModel,it.id) }
            rows += 1
        }
    }
}

@Composable
fun NodeContent(
    flick: Flick,
    viewModel: FlickViewModel,
    index: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UploadFlickItem(flick = flick, index = index, viewModel = viewModel)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UploadFlickItem(
    flick: Flick,
    modifier: Modifier = Modifier,
    index: Int,
    viewModel: FlickViewModel,
) {
    var showBottomSheet = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(120.dp)
                    .padding(8.dp)
            ) {
                if (flick.thumbnailUrl != null) {
                    val imageBitmap: ImageBitmap = flick.thumbnailUrl!!.asImageBitmap()
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder or error image
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .background(Color.LightGray)
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { showBottomSheet.value = true }
                )
            }

            Text(
                text = index.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (showBottomSheet.value) {
            Toast.makeText(LocalContext.current, flick.toString(), Toast.LENGTH_SHORT).show()
            FlickDetailsModal(
                flick = flick,
                flickViewModel = viewModel,
                showBottomSheet = showBottomSheet,
                id = flick.id,
            )
        }
    }
}
