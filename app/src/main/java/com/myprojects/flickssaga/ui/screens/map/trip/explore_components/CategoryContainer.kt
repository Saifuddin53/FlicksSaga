package com.myprojects.flickssaga.ui.screens.map.trip.explore_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun CategoryContainer(
    title: String,
    cardList: List<CategoryItemData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = Typography.bodyMedium.copy(
                color = Color(0xff595959),
                fontSize = 15.sp,
                fontWeight = FontWeight(400)
            ),
            modifier = modifier
        )
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement
                .spacedBy(14.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(cardList) { item ->
                CardItem(item)
            }
        }
    }
}