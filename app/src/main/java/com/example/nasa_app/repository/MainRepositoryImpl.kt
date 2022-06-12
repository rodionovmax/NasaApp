package com.example.nasa_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.db.dao.NasaDao
import com.example.nasa_app.network.api.AppState
import com.example.nasa_app.network.api.RemoteDataSource
import com.example.nasa_app.util.toCurrentPodEntity
import com.example.nasa_app.util.toFavoriteEntity
import com.example.nasa_app.util.toPictureModel
import kotlinx.coroutines.*
import java.lang.NullPointerException

class MainRepositoryImpl(
    private val localDataSource: NasaDao,
    private val remoteDataSource: RemoteDataSource
) : MainRepository {
    override fun getFavoritePicturesOfTheDay(): List<PictureModel> {
        var favoritePictures = listOf<PictureModel>()
        runBlocking {
            launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    favoritePictures = localDataSource.getAllFavorites().map { it.toPictureModel() }
                }
            }
        }
        return favoritePictures
    }

    override fun addPictureToFavorites(picture: PictureModel) {
        localDataSource.addPODToFavorites(picture.toFavoriteEntity())
    }

    override fun removePictureFromFavorites(picture: PictureModel): List<PictureModel> {
        // Delete picture from favorites
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.removePODToFavorites(picture.toFavoriteEntity())
        }
        // Get the updated list of favorites
        var favoritePictures = listOf<PictureModel>()
        runBlocking {
            launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    favoritePictures = localDataSource.getAllFavorites().map { it.toPictureModel() }
                }
            }
        }
        return favoritePictures
    }

    override fun getPictureOfTheDay(): PictureModel {
        var currentPicture = PictureModel()
        try {
            runBlocking {
                launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        currentPicture = localDataSource.getCurrentPOD().toPictureModel()
                    }
                }
            }
        } catch (e : NullPointerException) {
            e.printStackTrace()
        }

        return currentPicture
    }

    override fun saveToCurrentPOD(picture: PictureModel) {
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.saveCurrentPOD(picture.toCurrentPodEntity())
        }
    }

    override fun getMarsPhotosList(camera : String): LiveData<AppState> {
        return remoteDataSource.getMarsPhotosList(camera)
    }


}


