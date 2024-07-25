package com.myprojects.flickssaga.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects
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

    suspend fun getPosts(): List<Post> {
        return try {
            val snapshot = postsCollection.get().await()
            snapshot.toObjects<Post>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun addSnapshotListener(onSnapshot: (List<Post>) -> Unit) {
        postsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val newPosts = snapshot.toObjects(Post::class.java)
                    onSnapshot(newPosts)
                }
            }
    }
}
