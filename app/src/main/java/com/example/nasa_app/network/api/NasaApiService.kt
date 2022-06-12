package com.example.nasa_app.network.api

import com.example.nasa_app.network.models.MarsPhotosListDto
import com.example.nasa_app.network.models.response.PodDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PodDto>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotos(
        @Query("api_key") apiKey: String,
        @Query("sol") sol: Int,
        @Query("camera") camera: String,
    ) : MarsPhotosListDto
}
