package com.myprojects.flickssaga.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.data.firebase.FireStoreUtil
import com.myprojects.flickssaga.data.firebase.FireStoreUtil.getPosts
import com.myprojects.flickssaga.data.firebase.FirebaseStorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoPostViewModel: ViewModel() {

    private val _postId = MutableStateFlow(0)
    val postId = _postId.asStateFlow()

    private val _currentPostIndex = MutableStateFlow(-1)
    val currentPostIndex = _currentPostIndex.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    init {
        initializePostId()
        fetchPosts()
    }

    private fun initializePostId() {
        viewModelScope.launch {
            _postId.value = FireStoreUtil.initializePostId()
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = FireStoreUtil.getPosts()
        }

        FireStoreUtil.addSnapshotListener { newPosts ->
            _posts.value = newPosts
        }
    }

    fun uploadFilesAndSavePost(videoUri: Uri, imageUri: Uri?, post: Post) {
        CoroutineScope(Dispatchers.IO).launch {
            val videoUrl = FirebaseStorageUtil.uploadFile(videoUri, "videos", "${post.id}.mp4")
            val imageUrl = imageUri?.let { FirebaseStorageUtil.uploadFile(it, "thumbnails", "${post.id}.jpg") }

            if (videoUrl != null) {
                val updatedPost = post.copy(videoUrl = videoUrl, imageUrl = imageUrl)
                FireStoreUtil.savePost(updatedPost)
            }
        }
    }


    fun uploadImagesAndSavePost(images: List<Uri>, post: Post) {
        var id = 0
        CoroutineScope(Dispatchers.IO).launch {
            var imagesUrl: List<String?> = listOf()
            images.forEach { imageUri ->
                val imageUrl = imageUri.let { FirebaseStorageUtil.uploadFile(it, "images", "$id${post.id}.jpg") }
                id++
                imagesUrl = imagesUrl + imageUrl
            }

            if(imagesUrl.isNotEmpty()) {
                val updatedPost = post.copy(images = imagesUrl)
                FireStoreUtil.savePost(updatedPost)
            }
        }
    }


    fun bitmapToFile(context: Context, bitmap: Bitmap?, fileName: String): Uri {
        val file = File(context.cacheDir, fileName)
        FileOutputStream(file).use { out ->
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return Uri.fromFile(file)
    }

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

    fun incrementPostId() {
        _postId.value++
    }

    fun setCurrentPostIndex(index: Int) {
        _currentPostIndex.value = index
    }
}