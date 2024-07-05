package com.myprojects.flickssaga.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.FileUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.FlickBinaryTree
import com.myprojects.flickssaga.data.FlickState
import com.myprojects.flickssaga.repositories.FlickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream

class FlickViewModel(private val flickRepository: FlickRepository): ViewModel() {
    private val _flicks = MutableStateFlow(flickRepository.flicksBinaryTree)
    val flicks = _flicks.asStateFlow()

    private val _flickState = MutableStateFlow<FlickState>(FlickState.Ready)
    val flickState = _flickState.asStateFlow()

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

    fun uploadThumbnail() {
        _currentFlicks.value = _currentFlicks.value.copy(thumbnailUrl = _currentFlicks.value.videoUrl?.let {
            getVideoThumbnail(
                it
            )
        })
    }

    fun getVideoThumbnail(videoUrl: String): Bitmap? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(videoUrl, HashMap<String, String>())
            retriever.frameAtTime
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
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


}

