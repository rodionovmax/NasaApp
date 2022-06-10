package com.example.nasa_app.data.api

sealed class PODData {
    data class Success(val serverResponseData: ServerResponsePOD) : PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
