package com.myprojects.flickssaga.data

sealed class FlickState {
    object Idle : FlickState()
    object Buffering : FlickState()
    object Ready : FlickState()
    object Ended : FlickState()
    object Changed: FlickState() {
        var currentFlick: Flick? = null
    }
    data class Error(val message: String) : FlickState()
}