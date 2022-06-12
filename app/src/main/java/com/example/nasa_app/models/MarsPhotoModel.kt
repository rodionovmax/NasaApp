package com.example.nasa_app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsPhotoModel(
    val id: Long = 0,
    val cameraId: Int = 20,
    val cameraName: String = "FHAZ",
    val roverId: Int = 5,
    val imgSrc: String = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00100/opgs/edr/fcam/FRA_406374643EDR_F0050178FHAZ00301M_.JPG",
    val earthDate: String = "2012-11-16",
) : Parcelable



