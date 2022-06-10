package com.example.nasa_app.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServerResponseMarsPhotos(
    @SerializedName("photos")
    var photos: ArrayList<MarsPhotoDTO>,
) : Parcelable

@Parcelize
data class MarsPhotoDTO(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("sol") var solDate: Int = 1000,
    @SerializedName("camera") var camera: CameraDTO = CameraDTO(),
    @SerializedName("img_src") var imageSrc: String = "",
    @SerializedName("earth_date") var earthDate: String = "2022-06-01",
    @SerializedName("rover") var rover: RoverDTO = RoverDTO(),
) : Parcelable

@Parcelize
data class CameraDTO(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var cameraName: String = "FHAZ",
    @SerializedName("rover_id") var roverId: Int = 5,
    @SerializedName("full_name") var cameraFullName: String = "Front Hazard Avoidance Camera",
) : Parcelable

@Parcelize
data class RoverDTO(
    @SerializedName("id") var id: Int = 5,
    @SerializedName("name") var roverName: String = "Curiosity",
    @SerializedName("landing_date") var landingDate: String = "2012-08-06",
    @SerializedName("launch_date") var launchDate: String = "2011-11-26",
    @SerializedName("status") var status: String = "active",
) : Parcelable
