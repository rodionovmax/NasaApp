package com.example.nasa_app.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.BuildConfig
import com.example.nasa_app.data.api.PODData
import com.example.nasa_app.data.api.RetrofitImpl
import com.example.nasa_app.data.api.ServerResponsePOD
import com.example.nasa_app.util.getDateToday
import com.example.nasa_app.util.getDateTwoDaysAgo
import com.example.nasa_app.util.getYesterdayDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODData> =
        MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getData(day : Int): LiveData<PODData> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var date : String = ""
            when(day) {
                1 -> date = getDateToday()
                2 -> date = getYesterdayDate()
                3 -> date = getDateTwoDaysAgo()
            }
            sendServerRequest(date)
        } else {
            sendServerRequest("2022-05-21")
        }
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = PODData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(object :
                Callback<ServerResponsePOD> {
                override fun onResponse(
                    call: Call<ServerResponsePOD>,
                    response: Response<ServerResponsePOD>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PODData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PODData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PODData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ServerResponsePOD>, t:
                    Throwable
                ) {
                    liveDataForViewToObserve.value = PODData.Error(t)
                }
            })
        }
    }
}
