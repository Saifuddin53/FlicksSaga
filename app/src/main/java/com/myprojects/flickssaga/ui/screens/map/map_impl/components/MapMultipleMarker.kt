package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PatternItem
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(MapsComposeExperimentalApi::class)
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
            state = MarkerState(position = TeenDarwaja),
            icon = customMarker(context = LocalContext.current, number = 1)
        )
        Marker(
            state = MarkerState(position = KankariaLake),
            icon = customMarker(context = LocalContext.current, number = 2)
        )
        Marker(
            state = MarkerState(position = ManekChowk),
            icon = customMarker(context = LocalContext.current, number = 3)
        )
        Polyline(
            points = pathPoints,
            color = Color.Blue, // Path color
            width = 5f, // Path width
        )
    }
}

fun customMarker(context: Context, number: Int): BitmapDescriptor {
    val width = 80
    val height = 120  // Make the height longer to accommodate the point
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Draw background with a pointed bottom (marker-like shape)
    val paint = Paint().apply {
        color = Color(0xFFCE1515).toArgb()
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    // Draw the top circle part
    canvas.drawCircle(width / 2f, width / 2f, width / 2f, paint)

    // Draw the pointed triangle bottom
    val path = Path().apply {
        moveTo((width / 2).toFloat(), height.toFloat())  // Bottom point
        lineTo(0f, (width / 2f))  // Left point (edge of circle)
        lineTo(width.toFloat(), (width / 2f))  // Right point (edge of circle)
        close()  // Connect back to bottom point
    }
    canvas.drawPath(path, paint)

    // Draw the sequence number in the center of the circle
    val textPaint = Paint().apply {
        color = Color(0xFFFFFFFF).toArgb()
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    val xPos = canvas.width / 2
    val yPos = (canvas.width / 2 - (textPaint.descent() + textPaint.ascent()) / 2)
    canvas.drawText(number.toString(), xPos.toFloat(), yPos, textPaint)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

