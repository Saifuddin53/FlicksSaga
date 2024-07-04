package com.myprojects.flickssaga.viewmodels

import androidx.lifecycle.ViewModel
import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.FlickBinaryTree
import com.myprojects.flickssaga.data.FlickState
import com.myprojects.flickssaga.repositories.FlickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlickViewModel(private val flickRepository: FlickRepository): ViewModel() {
    private val _flicks = MutableStateFlow(flickRepository.flicksBinaryTree)
    val flicks = _flicks.asStateFlow()

    private val _flickState = MutableStateFlow<FlickState>(FlickState.Ready)
    val flickState = _flickState.asStateFlow()

    private val _currentFlicks = MutableStateFlow<Flick>(flickRepository.flicksBinaryTree.root!!)
    val currentFlicks = _currentFlicks.asStateFlow()


    init {
        _flicks.value = flickRepository.flicksBinaryTree
    }

    fun searchFlicks(id: Int) {
        _currentFlicks.value = flicks.value.search(id)!!
        _flickState.value = FlickState.Ready
    }

    fun setCurrentFlick(flick: Flick) {
        _currentFlicks.value = flick
    }

}