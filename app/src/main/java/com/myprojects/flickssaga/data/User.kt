package com.myprojects.flickssaga.data

data class User(
    val id: Int = 0,
    val image: Int = 0,
    val username: String = "",
    val email: String = "",
    var isSelected: Boolean = false
)
