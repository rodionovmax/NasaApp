package com.example.nasa_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PODModel (
    val id: Long = 0,
    val date: String = "2022-05-19",
    val url: String = "https://apod.nasa.gov/apod/image/2205/TLE_2022-05-16-02-59-35s1024.jpg",
    val title: String = "A Digital Lunar Eclipse",
    val explanation: String = "Recorded on May 15/16 this sequence of exposures follows...",
    val copyright: String = "Michael Cain"
) : Parcelable


