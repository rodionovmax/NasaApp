package com.example.nasa_app.repository

import com.example.nasa_app.models.PictureModel

interface MainRepository {
    fun getFavoritePicturesOfTheDay() : List<PictureModel>
    fun addPictureToFavorites(picture : PictureModel)
    fun removePictureFromFavorites(picture: PictureModel) : List<PictureModel>
    fun getPictureOfTheDay() : PictureModel
    fun saveToCurrentPOD(picture: PictureModel)
}
