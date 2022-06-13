package com.example.nasa_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasa_app.R
import com.example.nasa_app.ThemeListener
import com.example.nasa_app.ui.picture.PODFragment
import com.example.nasa_app.util.THEME

class MainActivity : AppCompatActivity(), ThemeListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        // get theme before activity is created
        val themeSelected = getThemeFromSharedPrefs()
        onThemeSelected(themeSelected)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commitNow()
        }
    }

    private fun getThemeFromSharedPrefs(): Int {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getInt(THEME, R.style.Theme_Space)
    }

    override fun onThemeSelected(theme: Int) {
        setTheme(theme)
    }

}
