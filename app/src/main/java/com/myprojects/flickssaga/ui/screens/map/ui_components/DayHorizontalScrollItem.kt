package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.DayTimeLineEntity
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayHorizontalScrollItem(dayTimeLine: List<DayTimeLineEntity>) {
    val selectedItemBoolean = remember { mutableStateOf(1) }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.padding(start = 4.dp, end = 6.dp).size(34.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xffF5F5F5)
            )
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.edit_timeline_icon),
                    contentDescription = "",
                    modifier = Modifier.size(14.dp))
            }
        }
        LazyRow() {
            items(dayTimeLine) { item ->
                DayComponentItem(item, selectedItemBoolean) { selectedTimeline ->
                    selectedItemBoolean.value = selectedTimeline.id
                    dayTimeLine.forEach {
                        it.selectedDateId = selectedTimeline.id
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DayHorizontalScrollItemPreview() {
    DayHorizontalScrollItem(listOf(
        DayTimeLineEntity(1, LocalDate.now(), 1, listOf()),
        DayTimeLineEntity(1, LocalDate.now(), 1, listOf()),
        DayTimeLineEntity(1, LocalDate.now(), 1, listOf()),
        DayTimeLineEntity(1, LocalDate.now(), 1, listOf()),
        DayTimeLineEntity(1, LocalDate.now(), 1, listOf())
    ))
}