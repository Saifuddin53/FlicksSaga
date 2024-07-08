package com.myprojects.flickssaga.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.FileUtils
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.FlickBinaryTree
import com.myprojects.flickssaga.data.FlickState
import com.myprojects.flickssaga.data.UploadStates
import com.myprojects.flickssaga.repositories.FlickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.InputStream

class FlickViewModel(private val flickRepository: FlickRepository): ViewModel() {
    private val _flicks = MutableStateFlow(flickRepository.flicksBinaryTree)
    val flicks = _flicks.asStateFlow()

    private val _flickState = MutableStateFlow<FlickState>(FlickState.Ready)
    val flickState = _flickState.asStateFlow()

    private val _uploadStates = MutableStateFlow<UploadStates>(UploadStates.Idle)
    val uploadState = _uploadStates.asStateFlow()

    private val _currentFlicks = MutableStateFlow<Flick>(flickRepository.flicksBinaryTree.root!!)
    val currentFlicks = _currentFlicks.asStateFlow()

    private val _flicksList = MutableStateFlow<List<Flick>>(flickRepository.flicks)
    val flicksList = _flicksList.asStateFlow()


    init {
        _flicks.value = flickRepository.flicksBinaryTree
    }

    fun searchFlicks(id: Int) {
        _currentFlicks.value = flicks.value.search(id)!!
        _flickState.value = FlickState.Ready
    }

    fun setCurrentFlick(flick: Flick) {
        _currentFlicks.value = flick
    }


    fun createTmpFileFromUri(uri: Uri) {
        _currentFlicks.value.videoUrl = uri.toString()
    }

//    fun uploadThumbnail() {
//        _currentFlicks.value = _currentFlicks.value.copy(thumbnailUrl = _currentFlicks.value.videoUrl?.let {
//            getVideoThumbnail(
//                this, it
//            )
//        })
//    }


    fun getVideoThumbnail(context: Context, videoUri: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        var pfd: ParcelFileDescriptor? = null
        return try {
            pfd = context.contentResolver.openFileDescriptor(videoUri, "r")
            pfd?.fileDescriptor?.let { fd ->
                retriever.setDataSource(fd)
                val frame = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                frame
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        } catch (e: RuntimeException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            pfd?.close()
            retriever.release()
        }
    }


    fun insertRoot(flick: Flick) {
        _flicks.value = flickRepository.insertRoot(flick)
        _flicksList.value = flickRepository.flicks
    }

    fun insertToLeft(parent: Flick, flick: Flick) {
        _flicks.value = flickRepository.insertToLeft(parent, flick)
        _flicksList.value = flickRepository.flicks
    }

    fun insertToRight(parent: Flick, flick: Flick) {
        _flicks.value = flickRepository.insertToRight(parent, flick)
        _flicksList.value = flickRepository.flicks
    }

    fun resetUploadState() {
        viewModelScope.launch {
            _uploadStates.value = UploadStates.Success
        }
    }

    fun resetUploadStateToIdle() {
        viewModelScope.launch {
            _uploadStates.value = UploadStates.Idle
        }
    }


}

