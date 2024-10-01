package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import android.content.Context
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.myprojects.flickssaga.ui.screens.map.map_impl.network.DirectionsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun FetchDirections(
    origin: LatLng,
    destination: LatLng,
    waypoints: List<LatLng>,
    onRouteReceived: (List<LatLng>) -> Unit
) {
    val context = LocalContext.current
    val apiKey = getApiKey(context)

    if (apiKey.isNullOrEmpty()) {
        Toast.makeText(context, "API key is missing", Toast.LENGTH_SHORT).show()
        return
    }

    // Retrofit setup
    val retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val directionsApi = retrofit.create(DirectionsApiService::class.java)

    // Prepare the origin, destination, and waypoints in the required format
    val originStr = "${origin.latitude},${origin.longitude}"
    val destinationStr = "${destination.latitude},${destination.longitude}"
    val waypointsStr = waypoints.joinToString("|") { "${it.latitude},${it.longitude}" }

    // Fetch directions
    LaunchedEffect(Unit) {
        try {
            // Use withContext(Dispatchers.IO) to ensure the network request is done on a background thread
            val response = withContext(Dispatchers.IO) {
                directionsApi.getDirections(
                    origin = originStr,
                    destination = destinationStr,
                    waypoints = waypointsStr,
                    apiKey = apiKey // Pass the dynamically fetched API key
                ).execute()
            }

            if (response.isSuccessful) {
                val route = response.body()?.routes?.firstOrNull()
                if (route != null) {
                    Log.d("DirectionsAPI", "Route fetched successfully")
                } else {
                    Log.e("DirectionsAPI", "No route returned")
                }
                route?.overviewPolyline?.points?.let {
                    Log.d("Polyline", "Polyline points: $it")
                    val decodedPath = PolyUtil.decode(it) // Decode the polyline
                    onRouteReceived(decodedPath)  // Callback to draw path
                }
            } else {
                Log.e("MapError", "Failed to get directions: ${response.message()}")
                Toast.makeText(context, "Failed to get directions", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("NetworkError", "Error fetching directions", e)
            Toast.makeText(context, "Error fetching directions", Toast.LENGTH_SHORT).show()
        }
    }
}


fun getApiKey(context: Context): String? {
    return try {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        appInfo.metaData?.getString("com.google.android.geo.API_KEY")
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}