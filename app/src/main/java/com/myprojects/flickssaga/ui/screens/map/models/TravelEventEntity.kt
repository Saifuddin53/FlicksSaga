package com.myprojects.flickssaga.ui.screens.map.models

data class TravelEventEntity(
    val id: Int,
    val title: String,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val imagesUrl: List<String>,
    val latitude: Double,
    val longitude: Double,
    val tags: List<String>,
    val distance: String
)
