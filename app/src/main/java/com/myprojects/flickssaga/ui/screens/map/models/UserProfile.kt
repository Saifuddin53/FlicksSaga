package com.myprojects.flickssaga.ui.screens.map.models

data class UserProfile(
    val name: String,
    val username: String,
    val profilePictureUrl: String, // URL of the profile image
    val address: String,
    val locationDetails: LocationDetails,
    val sharedConnection: String? = null, // Information like "Jay Rajput and you..."
)

data class LocationDetails(
    val locationName: String, // e.g., "Ji.Raya Yeh Gangga"
    val distance: String, // e.g., "200m"
    val travelTime: String // e.g., "5 min"
)
