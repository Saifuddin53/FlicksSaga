package com.myprojects.flickssaga.ui.screens.map.trip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.myprojects.flickssaga.ui.components.BottomNavigationBar
import com.myprojects.flickssaga.ui.screens.map.travelEvents
import com.myprojects.flickssaga.ui.screens.map.trip.components.TripBottomDialog
import com.myprojects.flickssaga.ui.screens.map.trip.components.TripItem
import com.myprojects.flickssaga.ui.screens.map.trip.components.TripTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TripTopBar(modifier = Modifier.padding(horizontal = 8.dp))
            LazyColumn {
                items(travelEvents) {
                    TripItem(
                        travelEventEntity = it,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        showBottomDialog = !showBottomDialog
                    }
                }
            }

            if (showBottomDialog) {
                TripBottomDialog(
                    sheetState,
                    modifier = Modifier.padding(16.dp)
                ) {
                    showBottomDialog = false
                }
            }
        }
    }
}