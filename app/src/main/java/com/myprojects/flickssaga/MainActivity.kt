package com.myprojects.flickssaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myprojects.flickssaga.repositories.FlickRepository
import com.myprojects.flickssaga.ui.screens.Check
import com.myprojects.flickssaga.ui.theme.FlicksSagaTheme
import com.myprojects.flickssaga.viewmodels.FlickViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val flickViewModel = FlickViewModel(FlickRepository())
        setContent {
            FlicksSagaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Check(
                        flickViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
