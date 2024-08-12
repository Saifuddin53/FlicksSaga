package com.myprojects.flickssaga.data

import com.google.firebase.Timestamp

data class Notification(
    val id: Int,
    val username: String?,
    val message: String?,
    val timestamp: Long?,
    val category: CATEGORY?
)

enum class CATEGORY { TAGSANDMENTION, COMMENT, LIKE, FOLLOW, MARKETPLACE, DISCUSSION, VIOLATION}

fun CATEGORY.toReadableString(): String {
    return when (this) {
        CATEGORY.TAGSANDMENTION -> "Tags and Mentions"
        CATEGORY.COMMENT -> "Comment"
        CATEGORY.LIKE -> "Like"
        CATEGORY.FOLLOW -> "Follow"
        CATEGORY.MARKETPLACE -> "Marketplace"
        CATEGORY.DISCUSSION -> "Discussion"
        CATEGORY.VIOLATION -> "Violation"
    }
}