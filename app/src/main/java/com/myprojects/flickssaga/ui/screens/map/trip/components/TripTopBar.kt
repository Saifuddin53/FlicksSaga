package com.myprojects.flickssaga.ui.screens.map.trip.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun TripTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .border(width = 1.dp,shape = CircleShape,color = Color.LightGray)
        ) {
            IconButton(
                onClick = { /* Handle back button click */ },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 4.dp)
                )
            }
        }
        Text(
            text = "Bookmarks",
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.W500),
        )
        Box(
            modifier = Modifier
                .size(36.dp)
                .border(width = 1.dp,shape = CircleShape,color = Color.LightGray)
        ) {
            IconButton(
                onClick = { /* Handle back button click */ },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }
    }
}