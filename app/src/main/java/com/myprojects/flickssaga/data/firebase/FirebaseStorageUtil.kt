package com.myprojects.flickssaga.data.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object FirebaseStorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    private val storageRef = storageInstance.reference

    suspend fun uploadFile(fileUri: Uri, folder: String, fileName: String): String? {
        return try {
            val fileRef = storageRef.child("$folder/$fileName")
            val uploadTask = fileRef.putFile(fileUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}