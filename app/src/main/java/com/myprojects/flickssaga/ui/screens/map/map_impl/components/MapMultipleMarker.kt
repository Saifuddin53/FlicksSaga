package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PatternItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapMultipleMarker(modifier: Modifier = Modifier) {
    val TeenDarwaja = LatLng(23.0154404, 72.5767402)
    val KankariaLake = LatLng(22.9786, 72.6031)
    val ManekChowk = LatLng(23.0271, 72.5895)

    val pathPoints = listOf(
        TeenDarwaja,
        KankariaLake,
        ManekChowk
    )

    val builder = LatLngBounds.builder()
    builder.include(TeenDarwaja)
    builder.include(KankariaLake)
    builder.include(ManekChowk)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(builder.build().center, 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        Marker(
            state = MarkerState(position = TeenDarwaja)
        )
        Marker(
            state = MarkerState(position = KankariaLake)
        )
        Marker(
            state = MarkerState(position = ManekChowk)
        )
        Polyline(
            points = pathPoints,
            color = Color.Blue, // Path color
            width = 5f, // Path width
        )
    }
}