package com.example.nasa_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nasa_app.databinding.ActivityMainBinding
import com.example.nasa_app.ui.explore.ExploreFragment
import com.example.nasa_app.ui.favorites.FavoritesFragment
import com.example.nasa_app.ui.pod.PODFragment
import com.example.nasa_app.ui.settings.SettingsFragment
import com.example.nasa_app.util.THEME

class MainActivity : AppCompatActivity(), ThemeListener, FragmentNav {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // get theme before activity is created
        val themeSelected = getThemeFromSharedPrefs()
        onThemeSelected(themeSelected)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.host_container, PODFragment.newInstance())
                .commitNow()
        }

        setBottomNavigation()
    }

    private fun getThemeFromSharedPrefs(): Int {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        return sharedPreferences.getInt(THEME, R.style.Theme_Space)
    }

    override fun onThemeSelected(theme: Int) {
        setTheme(theme)
    }

    private fun setBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_home -> {
                    openFragment(PODFragment.newInstance())
                    true
                }
                R.id.bottom_view_gallery -> {
                    openFragment(FavoritesFragment.newInstance())
                    true
                }
                R.id.bottom_view_explore -> {
                    openFragment(ExploreFragment.newInstance())
                    true
                }
                R.id.bottom_view_settings -> {
                    openFragment(SettingsFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host_container, fragment)
            .addToBackStack("")
            .commit()
    }


}
