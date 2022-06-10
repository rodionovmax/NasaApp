package com.example.nasa_app.data.repository

import com.example.nasa_app.data.models.PODModel
import com.example.nasa_app.room.dao.PODDao
import com.example.nasa_app.util.toCurrentPodEntity
import com.example.nasa_app.util.toPodEntity
import com.example.nasa_app.util.toPodModel
import kotlinx.coroutines.*
import java.lang.NullPointerException

class LocalRepositoryImpl(
    private val localDataSource: PODDao,
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
        localDataSource.addPODToFavorites(picture.toPodEntity())
    }

    override fun removePictureFromFavorites(picture: PODModel): List<PODModel> {
        // Delete picture from favorites
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.removePODToFavorites(picture.toPodEntity())
        }
        // Get the updated list of favorites
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

    override fun getPictureOfTheDay(): PODModel {
        var currentPicture: PODModel = PODModel(
            0,
            "2022-05-19",
            "https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s1024.jpg",
            "A Digital Lunar Eclipse",
            "Recorded on May 15/16 this sequence of exposures follows...",
            "Michael Cain"
        )
        try {
            runBlocking {
                launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        currentPicture = localDataSource.getCurrentPOD().toPodModel()
                    }
                }
            }
        } catch (e : NullPointerException) {
            e.printStackTrace()
        }

        return currentPicture
    }

    override fun saveToCurrentPOD(picture: PODModel) {
        localDataSource.saveCurrentPOD(picture.toCurrentPodEntity())
    }


}


