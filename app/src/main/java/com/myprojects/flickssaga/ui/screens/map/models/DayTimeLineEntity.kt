package com.myprojects.flickssaga.ui.screens.map.models

import java.time.LocalDate

data class DayTimeLineEntity(
    val id: Int,
    val day: LocalDate,
    var selectedDateId: Int,
    val travelEvents: List<TravelEventEntity>
)
