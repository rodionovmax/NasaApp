package com.example.nasa_app.util

import android.R
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.nasa_app.models.MarsPhotoModel
import com.example.nasa_app.network.models.MarsPhotoDTO
import com.example.nasa_app.network.models.MarsPhotosListDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

const val THEME = "Theme"
const val RADIO_BTN_THEME = "RadioButtonTheme"

@RequiresApi(Build.VERSION_CODES.O)
fun getDateToday(): String {
    val date = LocalDate.now()
    val text: String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}

@RequiresApi(Build.VERSION_CODES.O)
fun getYesterdayDate(): String {
    val date = LocalDate.now().minusDays(1)
    val text: String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateTwoDaysAgo(): String {
    val date = LocalDate.now().minusDays(2)
    val text: String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}

fun showToast(context: Context, msg: String) {

    // Creating new instance of Toast
    val toast = Toast.makeText(context," $msg ", Toast.LENGTH_SHORT)

    // Getting the View
    val view: View? = toast.view

    view?.let {
        val text = view.findViewById(R.id.message) as TextView

        // Setting the Text Appearance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            text.setTextAppearance(com.example.nasa_app.R.style.styledToast)
        }
    }
    // Showing the Toast Message
    toast.show()
}

fun convertMarsPhotoDtoToMarsPhotoModel(marsPhotoListDTO: MarsPhotosListDto) : List<MarsPhotoModel> {
    val marsPhotos = marsPhotoListDTO.photos.map {
        MarsPhotoModel(
            id = it.id,
            cameraId = it.camera.id,
            cameraName = it.camera.cameraName,
            roverId = it.rover.id,
            imgSrc = it.imageSrc,
            earthDate = it.earthDate
        )
    }
    return marsPhotos
}

