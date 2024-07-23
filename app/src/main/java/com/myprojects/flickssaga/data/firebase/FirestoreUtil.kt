package com.myprojects.flickssaga.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.myprojects.flickssaga.data.Post
import kotlinx.coroutines.tasks.await

object FireStoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val postsCollection = firestoreInstance.collection("posts")

    suspend fun savePost(post: Post) {
        try {
            postsCollection.document(post.id.toString()).set(post).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
