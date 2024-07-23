package com.myprojects.flickssaga.data

data class Post(
    val id: Int,
    val videoUrl: String? = null,
    val imageUrl: String? = null,
    val title: String? = null,
    val description: String? = null,
    val timestamp: Long? = null
)
