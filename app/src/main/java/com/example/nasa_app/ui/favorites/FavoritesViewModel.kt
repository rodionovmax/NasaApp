package com.example.nasa_app.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.App
import com.example.nasa_app.models.PictureModel
import com.example.nasa_app.repository.MainRepository
import com.example.nasa_app.repository.MainRepositoryImpl
import com.example.nasa_app.network.api.AppState
import com.example.nasa_app.network.api.RemoteDataSource

class FavoritesViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val mainRepository: MainRepository = MainRepositoryImpl(App.getDB().nasaDao, RemoteDataSource())
) : ViewModel() {

    fun getFavoritePictures() {
        liveDataToObserve.value = AppState.Success(mainRepository.getFavoritePicturesOfTheDay())
    }

    fun addPictureToFavorites(picture : PictureModel) {
        liveDataToObserve.value = AppState.Success(mainRepository.addPictureToFavorites(picture))
    }

    fun removePictureFromFavorites(picture: PictureModel) {
        liveDataToObserve.value = AppState.Success(mainRepository.removePictureFromFavorites(picture))
    }

    fun savePictureAsPOD(picture: PictureModel) {
        mainRepository.saveToCurrentPOD(picture)
    }

    fun getPictureOfTheDay() : PictureModel {
        return mainRepository.getPictureOfTheDay()
    }
}
