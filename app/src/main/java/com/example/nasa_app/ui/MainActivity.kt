package com.example.nasa_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasa_app.R
import com.example.nasa_app.ui.home.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}