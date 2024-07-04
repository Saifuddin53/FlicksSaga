package com.myprojects.flickssaga.data

sealed class FlickState {
    object Idle : FlickState()
    object Buffering : FlickState()
    object Ready : FlickState()
    object Ended : FlickState()
    object Changed: FlickState() {
        var currentFlick: Flick? = null
        fun setFlick(flick: Flick) {
            currentFlick = flick
        }
    }
    data class Error(val message: String) : FlickState()
}