package com.example.nasa_app.network.api

import com.example.nasa_app.network.models.response.POD

sealed class PODState {
    data class Success(val serverResponseData: POD) : PODState()
    data class Error(val error: Throwable) : PODState()
    data class Loading(val progress: Int?) : PODState()
}
