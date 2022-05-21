package com.example.nasa_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.ui.about.AboutFragment
import com.example.nasa_app.ui.chips.ChipsFragment
import com.example.nasa_app.ui.favorites.FavoritesFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.bottom_navigation_layout, container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    openFragment(PictureOfTheDayFragment.newInstance())
                }
                R.id.navigation_favorites -> {
                    openFragment(FavoritesFragment.newInstance())
                }
                R.id.navigation_about -> {
                    openFragment(AboutFragment.newInstance())
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

}