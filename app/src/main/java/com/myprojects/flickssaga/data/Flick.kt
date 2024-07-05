package com.myprojects.flickssaga.data

import android.graphics.Bitmap

data class Flick(
    val id: Int,
    var videoUrl: String? = null,
    var thumbnailUrl: Bitmap? = null,
    var title: String? = null,
    var description: String? = null,
    var leftFlick: Flick? = null,
    var rightFlick: Flick? = null,
    var previous: Flick? = null
) {
    override fun toString(): String {
        return "Flick(id=$id, videoUrl='$videoUrl', title='$title', description='$description')"
    }
}



