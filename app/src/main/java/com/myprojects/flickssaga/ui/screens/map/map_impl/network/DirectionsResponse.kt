package com.myprojects.flickssaga.ui.screens.map.map_impl.network

data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val overviewPolyline: OverviewPolyline
)

data class OverviewPolyline(
    val points: String
)
