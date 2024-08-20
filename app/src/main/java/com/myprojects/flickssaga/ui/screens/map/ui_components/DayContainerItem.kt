package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.ui.screens.map.models.DayTimeLineEntity
import com.myprojects.flickssaga.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayComponentItem(dayTimeLineEntity: DayTimeLineEntity) {
    Card(
        modifier = Modifier, 
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (dayTimeLineEntity.isSelected) Color(0xff00A3FF) else Color(0xffF5F5F5)
        )
    ) {
        Box(modifier = Modifier
            .padding(12.dp)
            .clickable { dayTimeLineEntity.isSelected = !dayTimeLineEntity.isSelected }) {
            Text(
                text = "Day ${dayTimeLineEntity.id} | ${formatDateItem(dayTimeLineEntity.day)}",
                style = Typography.bodyMedium.copy(
                    color = if (dayTimeLineEntity.isSelected) Color.White else Color.Black
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateItem(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("E dd/yy")
    return date.format(formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DayComponentItemPreview() {
    DayComponentItem(DayTimeLineEntity(1, LocalDate.now(), true, listOf()))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DayComponentItemPreview2() {
    DayComponentItem(DayTimeLineEntity(1, LocalDate.now(), false, listOf()))
}