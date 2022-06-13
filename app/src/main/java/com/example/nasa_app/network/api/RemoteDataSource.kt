package com.example.nasa_app.network.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasa_app.BuildConfig
import com.example.nasa_app.network.models.MarsPhotosListDto
import com.example.nasa_app.util.convertMarsPhotoDtoToMarsPhotoModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


private const val BASE_URL = "https://api.nasa.gov/"
private const val SERVER_ERROR = "Server error"
private const val CORRUPTED_DATA = "Incorrect data"


class RemoteDataSource {

    fun getRetrofitImpl(): NasaApiService {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(createOkHttpClient(PODInterceptor()))
            .build()
        return podRetrofit.create(NasaApiService::class.java)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        return httpClient.build()
    }

    inner class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }

    fun getMarsPhotosList(camera : String): LiveData<AppState> {
        val data = MutableLiveData<AppState>()
        GlobalScope.launch(Dispatchers.IO) {
            val nasaAPI = getRetrofitImpl()
            val response = nasaAPI.getMarsPhotos(BuildConfig.NASA_API_KEY, sol = 1000, camera, page = 1)
            data.postValue(checkServerError(response))
        }
        return data
    }

    private fun checkServerError(response: MarsPhotosListDto): AppState {
        return if (response != null) {
            checkResponse(response)
        } else {
            AppState.Error(Throwable(SERVER_ERROR))
        }
    }

    private fun checkResponse(serverResponse: MarsPhotosListDto): AppState {
        return if (serverResponse.photos == null) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertMarsPhotoDtoToMarsPhotoModel(serverResponse))
        }
    }
}
