package com.myprojects.flickssaga

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.theme.FlicksSagaTheme
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                Toast.makeText(this, "Read media video permission granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Read media video permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }

    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            // All permissions are granted
            Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
        } else {
            // At least one permission is denied
            Toast.makeText(this, "Permissions denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlicksSagaTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Scaffold(
                ) { innerpadding ->
                    NavigationHost(navController, modifier = Modifier.padding(innerpadding), this)
                }
            }
        }
        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission() {
        when {
            allPermissionsGranted() -> {
                // Permissions are already granted
//                Toast.makeText(this, "Permissions already granted!", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale() -> {
                // Show an explanation to the user
                Toast.makeText(this, "Permissions are required for this app.", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(permissions)
            }
            else -> {
                // Directly request for required permissions
                requestPermissionLauncher.launch(permissions)
            }
        }
    }

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale() = permissions.any {
        ActivityCompat.shouldShowRequestPermissionRationale(this, it)
    }
}

