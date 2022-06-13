package com.example.nasa_app.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

@RequiresApi(Build.VERSION_CODES.O)
fun getDateToday() : String {
    val date = LocalDate.now()
    val text : String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}

@RequiresApi(Build.VERSION_CODES.O)
fun getYesterdayDate() : String {
    val date = LocalDate.now().minusDays(1)
    val text : String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateTwoDaysAgo() : String {
    val date = LocalDate.now().minusDays(2)
    val text : String = date.format(ISO_LOCAL_DATE)
    Log.d("getDate", "date: $date, text: $text")
    return text
}