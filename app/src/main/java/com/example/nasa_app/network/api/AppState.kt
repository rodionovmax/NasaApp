package com.example.nasa_app.network.api

sealed class AppState {
    data class Success(val success: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
