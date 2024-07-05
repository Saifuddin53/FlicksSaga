package com.myprojects.flickssaga.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.FlickState
import com.myprojects.flickssaga.viewmodels.FlickViewModel

@Composable
fun NextScreenQuestion(
    currentFlick: Flick,
    flickState: MutableState<FlickState>
) {
    Box(
        modifier = Modifier.fillMaxSize()
        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           Text(
               text = "Choose your next flick",
               fontSize = 30.sp,
               fontWeight = FontWeight.Bold,
               color = Color.White,
               modifier = Modifier.padding(vertical = 30.dp)
           )
            if(currentFlick.previous != null) {
                Button(
                    onClick = {
                        FlickState.Changed.currentFlick = currentFlick.previous
                        flickState.value = FlickState.Changed
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Row {
                        Icon(painter = painterResource(id = R.drawable.icon_back)
                            , contentDescription = "Go previous",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 10.dp)
                                .size(20.dp))
                        Text(
                            text = currentFlick.previous?.title ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            if(currentFlick.leftFlick != null) {
                Button(
                    onClick = {
                        FlickState.Changed.currentFlick = currentFlick.leftFlick
                        flickState.value = FlickState.Changed
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.White
                    ),
                ) {
                    Row {
                        Text(
                            text = currentFlick.leftFlick?.title ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(painter = painterResource(id = R.drawable.icon_forward)
                            , contentDescription = "Go forward",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                                .size(20.dp))
                    }
                }
            }
            if(currentFlick.rightFlick != null) {
                Button(
                    onClick = {
                        FlickState.Changed.currentFlick = currentFlick.rightFlick
                        flickState.value = FlickState.Changed
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Row {
                        Text(
                            text = currentFlick.rightFlick?.title ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(painter = painterResource(id = R.drawable.icon_forward)
                            , contentDescription = "Go forward",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                                .size(20.dp))
                    }
                }
            }
        }
    }
}