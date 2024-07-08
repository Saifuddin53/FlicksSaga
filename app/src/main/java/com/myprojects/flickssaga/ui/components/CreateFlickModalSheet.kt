package com.myprojects.flickssaga.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.UploadStates
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickDetailsModal(
    flick: Flick,
    flickViewModel: FlickViewModel,
    showBottomSheet: MutableState<Boolean>,
    id: Int,
) {
    val context = LocalContext.current
    val newFlick = Flick(id)
    val currentFlicks = flickViewModel.currentFlicks.collectAsState()

    var id = id

    var videoUrlString = remember { mutableStateOf(newFlick.videoUrl) }
    var title = remember { mutableStateOf(newFlick.title) }
    var description = remember { mutableStateOf(newFlick.description) }

    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var isRight = remember { mutableStateOf(false) }
    var isLeft = remember { mutableStateOf(false) }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                videoUrlString.value = uri.toString()
            }
        }
    )

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("Create a Flick", fontSize = 20.sp, color = Color.White)

            Button(
                onClick = {
                    videoPicker.launch("video/*")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Upload video",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text("Create branch", fontSize = 20.sp, color = Color.White)

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isLeft.value, onCheckedChange = { isLeft.value = !isLeft.value })
                Text("Left", fontSize = 20.sp, color = Color.White)
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isRight.value, onCheckedChange = { isRight.value = !isRight.value })
                Text("Right", fontSize = 20.sp, color = Color.White)
            }

            OutlinedTextField(
                value = title.value ?: "",
                onValueChange = { title.value = it },
                label = { Text("Title", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            OutlinedTextField(
                value = description.value ?: "",
                onValueChange = { description.value = it },
                label = { Text("Description", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            Button(
                onClick = {
                    newFlick.videoUrl = videoUrlString.value
                    newFlick.title = title.value
                    newFlick.description = description.value
                    newFlick.thumbnailUrl = videoUrlString.value?.let {
                        flickViewModel.getVideoThumbnail(context, Uri.parse(it))
                    }

                    if (id == 0) {
                        Toast.makeText(context, "Root flick created", Toast.LENGTH_SHORT).show()
                        flickViewModel.insertRoot(newFlick)
                        if (isLeft.value) {
                            id += 1
                            flickViewModel.insertToLeft(newFlick, Flick(id))
                        }
                        if (isRight.value) {
                            id += 1
                            flickViewModel.insertToRight(newFlick, Flick(id))
                        }
                    } else {
                        val parentFlick = flick.previous
                        Toast.makeText(context, flick.toString(), Toast.LENGTH_SHORT).show()

                        if(parentFlick?.leftFlick?.id == id) {
                            parentFlick.leftFlick = newFlick
                            newFlick.previous = parentFlick
                        }else if(parentFlick?.rightFlick?.id == flick.id) {
                            parentFlick.rightFlick = newFlick
                            newFlick.previous = parentFlick
                        }


                        if (isLeft.value) {
                            id += 1
                            flickViewModel.insertToLeft(newFlick, Flick(id))
                        }
                        if (isRight.value) {
                            id += 1
                            flickViewModel.insertToRight(newFlick, Flick(id))
                        }

                    }

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            UploadStates.Success.newFlick = newFlick
                            flickViewModel.resetUploadState()
                            showBottomSheet.value = false
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Save")
            }
        }
    }
}

