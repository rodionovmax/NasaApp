package com.example.nasa_app.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.databinding.FragmentExploreBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ExploreFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewPager.adapter = ExplorePagerAdapter(this@ExploreFragment)

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()

            val dotsIndicator = dotsIndicator
            dotsIndicator.attachTo(viewPager)
        }
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            EARTH_FRAGMENT -> "Earth"
            MARS_FRAGMENT -> "Mars"
            WEATHER_FRAGMENT -> "Weather"
            else -> ""
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            EARTH_FRAGMENT -> R.drawable.ic_earth
            MARS_FRAGMENT -> R.drawable.ic_mars
            WEATHER_FRAGMENT -> R.drawable.ic_system
            else -> throw IndexOutOfBoundsException()
        }
    }
}
