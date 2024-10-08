package com.myprojects.flickssaga.ui.screens.map.trip.explore_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun CategoryItem(
    @DrawableRes iconResId: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "",
                modifier = Modifier
                    .padding(20.dp)
            )
        }
        Text(
            text = title,
            style = Typography.bodyMedium.copy(
                color = Color(0xff595959),
                fontSize = 11.sp,
                fontWeight = FontWeight(400)
            )
        )
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(
        iconResId = R.drawable.shop,
        title = "Category"
    )
}