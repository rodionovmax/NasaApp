package com.example.nasa_app.network.api

import com.example.nasa_app.network.models.response.PodDto

sealed class PODState {
    data class Success(val serverResponseData: PodDto) : PODState()
    data class Error(val error: Throwable) : PODState()
    data class Loading(val progress: Int?) : PODState()
}
