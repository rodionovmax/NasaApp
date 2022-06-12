package com.example.nasa_app.repository

import androidx.lifecycle.LiveData
import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.network.api.AppState

interface MainRepository {
    fun getFavoritePicturesOfTheDay() : List<PictureModel>
    fun addPictureToFavorites(picture : PictureModel)
    fun removePictureFromFavorites(picture: PictureModel) : List<PictureModel>
    fun getPictureOfTheDay() : PictureModel
    fun saveToCurrentPOD(picture: PictureModel)

    fun getMarsPhotosList(camera: String) : LiveData<AppState>
}
