package com.myprojects.flickssaga.data

data class Flick(
    val id: Int,
    val videoUrl: String,
    val tags: List<String>,
    val title: String,
    val description: String,
    var isPlaying: Boolean,
    var leftFlick: Flick? = null,
    var rightFlick: Flick? = null,
    var previous: Flick? = null
) {
    override fun toString(): String {
        return "Flick(id=$id, videoUrl='$videoUrl', tags=$tags, title='$title', description='$description', isPlaying=$isPlaying)"
    }
}



