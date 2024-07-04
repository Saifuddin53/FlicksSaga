package com.myprojects.flickssaga.viewmodels

import androidx.lifecycle.ViewModel
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.repositories.FlickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlickViewModel(private val flickRepository: FlickRepository): ViewModel() {
    private val _flicks = MutableStateFlow<List<Flick>>(emptyList())
    val flicks = _flicks.asStateFlow()

    private val _currentFlicks = MutableStateFlow<Flick>(flickRepository.getFlicksById(1))
    val currentFlicks = _currentFlicks.asStateFlow()

    init {
        _flicks.value = flickRepository.getFlicks()
    }

    fun setIsPlaying(id: Int, isPlaying: Boolean) {
        _flicks.value = flickRepository.setIsPlaying(id, isPlaying)
    }

    fun getFlicksById(id: Int) {
        _currentFlicks.value = flickRepository.getFlicksById(id)
    }

}