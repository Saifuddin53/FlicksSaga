package com.myprojects.flickssaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.screens.ShortViewCompose
import com.myprojects.flickssaga.ui.theme.FlicksSagaTheme
import com.myprojects.flickssaga.viewmodels.FlickViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val flickViewModel = FlickViewModel(FlickRepository())
        setContent {
            FlicksSagaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    FlicksScreen(flickViewModel)
                    ShortViewCompose(
                        viewModel = flickViewModel,
                        clickItemPosition = 0
                    )
                }
            }
        }
    }
}

