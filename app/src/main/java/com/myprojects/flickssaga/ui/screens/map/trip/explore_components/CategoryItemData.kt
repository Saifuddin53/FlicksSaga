package com.myprojects.flickssaga.ui.screens.map.trip.explore_components

import androidx.annotation.DrawableRes

data class CategoryItemData(
    val image: String,
    val title: String
)

data class CategoryTypeItemData(
    @DrawableRes val icon: Int,
    val type: String
)