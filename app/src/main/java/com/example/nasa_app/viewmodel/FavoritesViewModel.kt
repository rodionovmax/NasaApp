package com.example.nasa_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.App
import com.example.nasa_app.data.models.PODModel
import com.example.nasa_app.data.repository.LocalRepository
import com.example.nasa_app.data.repository.LocalRepositoryImpl
import com.example.nasa_app.data.api.AppState

class FavoritesViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getInstance())
) : ViewModel() {

    fun getFavoritePictures() {
        liveDataToObserve.value = AppState.Success(localRepository.getFavoritePicturesOfTheDay())
    }

    fun addPictureToFavorites(picture : PODModel) {
        liveDataToObserve.value = AppState.Success(localRepository.addPictureToFavorites(picture))
    }

    fun removePictureFromFavorites(picture: PODModel) {
        liveDataToObserve.value = AppState.Success(localRepository.removePictureFromFavorites(picture))
    }

    fun savePictureAsPOD(picture: PODModel) {
        localRepository.saveToCurrentPOD(picture)
    }

    fun getPictureOfTheDay() : PODModel {
        return localRepository.getPictureOfTheDay()
    }
}
