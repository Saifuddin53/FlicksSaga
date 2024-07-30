package com.myprojects.flickssaga.data

data class Post(
    val id: Int = 0,
    val videoUrl: String? = null,
    val imageUrl: String? = null,
    val images: List<String?>? = null,
    val title: String? = null,
    val description: String? = null,
    val timestamp: Long? = null
)
