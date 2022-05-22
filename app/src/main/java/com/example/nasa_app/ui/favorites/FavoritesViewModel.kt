package com.example.nasa_app.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.App
import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.network.models.getFavoritesHardcoded
import com.example.nasa_app.repository.LocalRepository
import com.example.nasa_app.repository.LocalRepositoryImpl
import com.example.nasa_app.ui.AppState

class FavoritesViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val favoritesRepository: LocalRepository = LocalRepositoryImpl(App.getInstance())
) : ViewModel() {

    fun getFavoritePictures() {
//        liveDataToObserve.value = AppState.Success(favoritesRepository.getFavoritePicturesOfTheDay())
        liveDataToObserve.value = AppState.Success(getFavoritesHardcoded())
    }

    fun addPictureToFavorites(picture : PODModel) {
        liveDataToObserve.value = AppState.Success(favoritesRepository.addPictureToFavorites(picture))
    }

    fun removePictureFromFavorites(picture: PODModel) {
        liveDataToObserve.value = AppState.Success(favoritesRepository.removePictureFromFavorites(picture))
    }
}