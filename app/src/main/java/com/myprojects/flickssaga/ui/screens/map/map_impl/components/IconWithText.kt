package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.ui.theme.Typography
import kotlin.math.sin

@Composable
fun IconWithText(
    @DrawableRes icon: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp),
            tint = Color(0xff595959),
        )
        Text(
            text = text,
            style = Typography.bodyMedium.copy(
                color = Color(0xff595959),
                fontSize = 12.sp
            ),
        )
    }
}