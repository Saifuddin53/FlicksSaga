package com.myprojects.flickssaga.ui.screens

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.myprojects.flickssaga.ui.components.FlickDetailsModal
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun UploadScreen(
    flickViewModel: FlickViewModel
) {
    val flicks = flickViewModel.flicks
    val currentFlicks = flickViewModel.currentFlicks.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var showBottomSheet = remember { mutableStateOf(false) }

    val thumbnail = remember { mutableStateOf(currentFlicks.value.thumbnailUrl) }

    var index = remember {
        mutableStateOf(0)
    }

//    LaunchedEffect(currentFlicks.value.thumbnailUrl) {
//        thumbnail.value = currentFlicks.value.thumbnailUrl
//    }

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
                navigationIcon = {
                },
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
        UploadFlickItem(flick = currentFlicks.value
            , modifier = Modifier.padding(innerPadding)
            , showBottomSheet = showBottomSheet,
            thumbnail = thumbnail)

        if (showBottomSheet.value) {
            FlickDetailsModal(currentFlicks.value, flickViewModel, id = index,  showBottomSheet = showBottomSheet)
        }
    }

}


@Composable
fun UploadFlickItem(flick: Flick,
                    modifier: Modifier = Modifier,
                    thumbnail: MutableState<Bitmap?>,
                    showBottomSheet: MutableState<Boolean>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(120.dp)
                    .padding(8.dp)
            ) {
                if (thumbnail.value != null) {
                    val imageBitmap: ImageBitmap = thumbnail.value!!.asImageBitmap()
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
                    Box(modifier = modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(Color.LightGray)) {
                    }
                }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_upload),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable { showBottomSheet.value = true }
                    )

            }
        }
    }
}
