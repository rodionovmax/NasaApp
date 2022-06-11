package com.example.nasa_app.ui.pod

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasa_app.BuildConfig
import com.example.nasa_app.network.api.PODState
import com.example.nasa_app.network.api.RetrofitImpl
import com.example.nasa_app.network.models.response.POD
import com.example.nasa_app.util.getDateToday
import com.example.nasa_app.util.getDateTwoDaysAgo
import com.example.nasa_app.util.getYesterdayDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODState> =
        MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    private var mutableDate = MutableLiveData<PODState>()

    fun setData(day : Int) {
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
    }

    fun getData() : LiveData<PODState> {
        return mutableDate
    }

    private fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = PODState.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODState.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(object :
                Callback<POD> {
                override fun onResponse(
                    call: Call<POD>,
                    response: Response<POD>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        mutableDate.value = PODState.Success(body)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            mutableDate.value =
                                PODState.Error(Throwable("Unidentified error"))
                        } else {
                            mutableDate.value =
                                PODState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(
                    call: Call<POD>, t:
                    Throwable
                ) {
                    mutableDate.value = PODState.Error(t)
                }
            })
        }
    }
}
