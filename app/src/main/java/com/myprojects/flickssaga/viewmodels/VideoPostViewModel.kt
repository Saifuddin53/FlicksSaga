package com.myprojects.flickssaga.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.data.firebase.FireStoreUtil
import com.myprojects.flickssaga.data.firebase.FirebaseStorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class VideoPostViewModel {

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
}