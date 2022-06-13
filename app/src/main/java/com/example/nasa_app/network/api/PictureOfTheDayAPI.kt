package com.example.nasa_app.network.api

import com.example.nasa_app.ui.picture.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PODServerResponseData>
}
