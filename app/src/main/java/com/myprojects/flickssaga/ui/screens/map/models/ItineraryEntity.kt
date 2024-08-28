package com.myprojects.flickssaga.ui.screens.map.models

import java.time.LocalDate

data class ItineraryEntity(
    val city: String,
    val state: String,
    val country: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val daysMap: Map<LocalDate, DayTimeLineEntity>
)
