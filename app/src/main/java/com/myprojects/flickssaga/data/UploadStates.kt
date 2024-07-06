package com.myprojects.flickssaga.data

sealed class UploadStates {
    object Idle : UploadStates()
    object Loading : UploadStates()
    object Success : UploadStates() {
        var newFlick: Flick? = null
    }
    object Error : UploadStates()
}