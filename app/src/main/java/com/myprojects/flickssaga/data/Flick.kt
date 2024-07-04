package com.myprojects.flickssaga.data

data class Flick(
    val id: Int,
    val videoUrl: String,
    val tags: List<String>,
    val title: String,
    val description: String,
    var isPlaying: Boolean,
    var leftFlick: Flick? = null,
    var rightFlick: Flick? = null
)



