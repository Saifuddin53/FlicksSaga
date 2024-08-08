package com.myprojects.flickssaga.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import com.myprojects.flickssaga.viewmodels.VideoPostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPost(
    videoPostViewModel: VideoPostViewModel,
    showBottomSheet: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val isCustomThumbnail = remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val images = remember { mutableStateOf<List<Uri>>(mutableListOf()) }

    val previewImage = remember { mutableStateOf<Boolean>(false) }

    val currentImg = remember { mutableStateOf<Uri?>(null) }

    var imagesSelected by remember { mutableStateOf(0) }

    val postId = videoPostViewModel.postId.collectAsState()

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
//                videoUrlString.value = uri.toString()
                videoUri = uri
            }
        }
    )

    val thumbnailPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = uri
            }
        }
    )

    val imagesPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                images.value += uri
                currentImg.value = uri
            }
        }
    )

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
    ) {
        if(previewImage.value && images.value.isNotEmpty()) {
            currentImg.value = images.value[images.value.size - 1]
            ListImagesPreview(images, previewImage)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    previewImage.value = !previewImage.value
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Preview",
                    fontSize = 15.sp,
                    color = Color.White,
                )
            }
            PreviewImage(uri = currentImg)

            Text("Upload Post", fontSize = 20.sp, color = Color.White)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Video Picker
//                Button(
//                    onClick = {
//                        videoPicker.launch("video/*")
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Text(
//                        text = "Upload video",
//                        fontSize = 15.sp,
//                        color = Color.White,
//                        modifier = Modifier.padding(end = 8.dp)
//                    )
//                }


                //Thumbnail Image Picker
//                Button(
//                    onClick = {
//                        thumbnailPicker.launch("image/*")
//                        isCustomThumbnail.value = true
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Text(
//                        text = "Upload thumbnail",
//                        fontSize = 15.sp,
//                        color = Color.White,
//                    )
//                }

                    Button(
                        onClick = {
                            imagesPicker.launch("image/*")
                            imagesSelected = images.value.size
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Upload Images",
                            fontSize = 15.sp,
                            color = Color.White,
                        )
                    }
                    Text(
                        text = "${images.value.size} images selected",
                        color = Color.White
                        )

            }

            Text("Enter details", fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))

            AnimatedTextField(strings[0], { string -> title.value = string })

            OutlinedTextField(
                value = description.value ?: "",
                onValueChange = { description.value = it },
                placeholder = { Text("Description", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )


            Button(
                onClick = {
                    if(isCustomThumbnail.value) {
                        imageUri = videoPostViewModel.getVideoThumbnail(context, videoUri!!)
                            .let { videoPostViewModel.bitmapToFile(context, it, "Thumbnail") }
                    }

                    if (title.value.isNotEmpty() && description.value.isNotEmpty() && videoUri != null) {
                        videoPostViewModel.incrementPostId()
                        val post = Post(id = postId.value, title = title.value, description = description.value, timestamp = System.currentTimeMillis())
                        coroutineScope.launch {
                            videoPostViewModel.uploadFilesAndSavePost(videoUri!!, imageUri, post)
                        }
                    } else if (title.value.isNotEmpty() && description.value.isNotEmpty() && images.value.isNotEmpty()) {
                        videoPostViewModel.incrementPostId()
                        val post = Post(id = postId.value, title = title.value, description = description.value, timestamp = System.currentTimeMillis())
                        coroutineScope.launch {
                            videoPostViewModel.uploadImagesAndSavePost(images.value, post)
                        }
                        Toast.makeText(context, "${images.value.size}", Toast.LENGTH_SHORT).show()
                    }

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet.value = false
                        }
                    }
                    Toast.makeText(context, "Post Uploaded Successfully", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Save")
            }
        }
    }
}


@Composable
fun PreviewImage(uri: MutableState<Uri?>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 16.dp, bottom = 16.dp)
            .clickable { /* TODO */ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
    ) {
        if(uri.value != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(uri.value),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ListImagesPreview(
    images: MutableState<List<Uri>>,
    previewImage: MutableState<Boolean>
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Preview ", fontSize = 20.sp, color = Color.White)

        LazyRow(
            modifier = Modifier
                .padding(top = 32.dp),
        ){
            items(images.value) { image ->
                ImageCardPreview(uri = image) {
                    images.value = images.value.filter { it != image }   // Updates the list of images
                }
            }

            item {
                Box(
                    modifier = Modifier
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center) // Center the inner Box within the outer Box
                            .size(72.dp) // Size of the outer box, adjust based on your circle size
                    ) {
                        // Draw the circle
                        Canvas(modifier = Modifier.matchParentSize()) {
                            drawCircle(
                                color = Color.Gray,
                                radius = size.minDimension / 2
                            )
                        }
                    }

                    // Icon button on top of the circle
                    IconButton(
                        onClick = { previewImage.value = false },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_upload),
                            contentDescription = "add",
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp) // Size of the icon
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCardPreview(
    uri: Uri,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .padding(end = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "add",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }
            }
        }
    }
}

