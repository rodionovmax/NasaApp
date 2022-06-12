package com.example.nasa_app.ui.explore.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.App
import com.example.nasa_app.network.api.AppState
import com.example.nasa_app.network.api.RemoteDataSource
import com.example.nasa_app.repository.MainRepository
import com.example.nasa_app.repository.MainRepositoryImpl

class MarsPhotosViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val mainRepository: MainRepository = MainRepositoryImpl(App.getDB().nasaDao, RemoteDataSource())
) : ViewModel()  {

    fun getLiveData() = liveData

    fun getMarsPhotos(camera : String) : LiveData<AppState> {
        return mainRepository.getMarsPhotosList(camera)
    }

//    fun getMarsPhotos(camera : String) {
//        liveData.value = AppState.Success(mainRepository.getMarsPhotosList(camera))
//    }
}

