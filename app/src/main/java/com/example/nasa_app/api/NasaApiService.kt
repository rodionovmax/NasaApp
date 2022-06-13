package com.example.nasa_app.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PODServerResponseData>
}
