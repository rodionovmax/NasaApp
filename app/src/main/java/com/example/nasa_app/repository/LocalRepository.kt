package com.example.nasa_app.repository

import com.example.nasa_app.network.models.PODModel

interface LocalRepository {
    fun getFavoritePicturesOfTheDay() : List<PODModel>
    fun addPictureToFavorites(picture : PODModel)
    fun removePictureFromFavorites(picture: PODModel)
    fun getPictureOfTheDay() : PODModel
    fun saveToCurrentPOD(picture: PODModel)
}