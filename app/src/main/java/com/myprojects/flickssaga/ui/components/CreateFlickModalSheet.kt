package com.myprojects.flickssaga.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickDetailsModal(
    flick: Flick,
    flickViewModel: FlickViewModel,
    showBottomSheet: MutableState<Boolean>,
    id: MutableState<Int>,

) {
    id.value += 1
    val newFlick = Flick(id.value)
    val currentFlicks = flickViewModel.currentFlicks.collectAsState()

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
            modifier = Modifier.fillMaxSize()
        ) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,) {
                Text("Create a Flick"
                    , fontSize = 20.sp
                    , color = Color.White)


                    Button(
                        onClick = {
                            // Save flick details or perform validation
                            newFlick.videoUrl = videoUrlString.value
                            newFlick.title = title.value
                            newFlick.description = description.value
                            newFlick.thumbnailUrl = videoUrlString.value?.let {
                                flickViewModel.getVideoThumbnail(
                                    it
                                )
                            }

                            if(id.value == 1) {
                                flickViewModel.insertRoot(newFlick)
                            } else {
                                if(isLeft.value) {
                                    flickViewModel.insertToLeft(currentFlicks.value, newFlick)
                                }
                                if(isRight.value) {
                                    flickViewModel.insertToRight(currentFlicks.value, newFlick)
                                }
                            }

                            scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet.value = false
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Save")
                    }
            }


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
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Text(
                text = "Create branch",
                fontSize = 20.sp,
                color = Color.White,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isLeft.value, onCheckedChange = { isLeft.value = !isLeft.value })
                Text(
                    text = "Left",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isRight.value, onCheckedChange = { isRight.value = !isRight.value })
                Text(
                    text = "Right",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }


            // Text field for title
            OutlinedTextField(
                value = title.value ?: "",
                onValueChange = { title.value = it },
                label = { Text("Title", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )


            // Text field for description
            OutlinedTextField(
                value = description.value ?: "",
                onValueChange = { description.value = it },
                label = { Text("Description", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            // Add more fields as needed


        }
    }


}
