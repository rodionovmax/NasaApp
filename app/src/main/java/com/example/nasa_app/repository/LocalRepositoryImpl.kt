package com.example.nasa_app.repository

import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.room.dao.PODDao
import com.example.nasa_app.util.toPodEntity
import com.example.nasa_app.util.toPodModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class LocalRepositoryImpl(
    private val localDataSource: PODDao
) : LocalRepository {
    override fun getFavoritePicturesOfTheDay(): List<PODModel> {
        var favoritePictures = listOf<PODModel>()
        runBlocking {
            launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    favoritePictures = localDataSource.getAllFavorites().map { it.toPodModel() }
                }
            }
        }
        return favoritePictures
    }

    override fun addPictureToFavorites(picture: PODModel) {
        // TODO: probably need coroutines here
        localDataSource.addPodToFavorites(picture.toPodEntity())
    }

    override fun removePictureFromFavorites(picture: PODModel) {
        // TODO: probably need coroutines here
        localDataSource.removePodToFavorites(picture.toPodEntity())
    }
}


