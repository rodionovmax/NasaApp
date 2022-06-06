package com.example.nasa_app.ui.explore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasa_app.ui.explore.earth.EarthFragment
import com.example.nasa_app.ui.explore.mars.MarsFragment
import com.example.nasa_app.ui.explore.weather.WeatherFragment

const val EARTH_FRAGMENT = 0
const val MARS_FRAGMENT = 1
const val WEATHER_FRAGMENT = 2

class ExplorePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[WEATHER_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }
}
