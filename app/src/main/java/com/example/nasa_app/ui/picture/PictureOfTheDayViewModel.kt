package com.example.nasa_app.ui.picture

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.BuildConfig
import com.example.nasa_app.util.getDateToday
import com.example.nasa_app.util.getDateTwoDaysAgo
import com.example.nasa_app.util.getYesterdayDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> =
        MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {
    fun getData(day : Int): LiveData<PictureOfTheDayData> {
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
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<PODServerResponseData>, t:
                    Throwable
                ) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }
}