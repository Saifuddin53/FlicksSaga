package com.myprojects.flickssaga.ui.components

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.data.UploadStates
import com.myprojects.flickssaga.viewmodels.FlickViewModel
import com.myprojects.flickssaga.viewmodels.VideoPostViewModel
import kotlinx.coroutines.launch
import java.util.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPost(
    videoPostViewModel: VideoPostViewModel,
    showBottomSheet: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var title = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }



    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
//                videoUrlString.value = uri.toString()
                videoUri = uri
            }
        }
    )

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.65f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Upload Post", fontSize = 20.sp, color = Color.White)

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

            Text("Enter details", fontSize = 20.sp, color = Color.White)

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

            Button(
                onClick = {

                    if (title.value.isNotEmpty() && description.value.isNotEmpty() && videoUri != null) {
                        val postId = Random().nextInt(Int.MAX_VALUE)
                        val post = Post(id = postId, title = title.value, description = description.value, timestamp = System.currentTimeMillis())
                        coroutineScope.launch {
                            videoPostViewModel.uploadFilesAndSavePost(videoUri!!, imageUri, post)
                        }
                    } else {
                        // Show error message
                    }

                    scope.launch { sheetState.hide() }.invokeOnCompletion {
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
    }
}

