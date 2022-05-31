package com.example.nasa_app.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasa_app.FragmentNav
import com.example.nasa_app.R
import com.example.nasa_app.databinding.BottomNavigationLayoutBinding
import com.example.nasa_app.databinding.FragmentFavoritesBinding
import com.example.nasa_app.ui.about.AboutFragment
import com.example.nasa_app.ui.favorites.FavoritesFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment() : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    openFragment(PODFragment.newInstance())
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
            .replace(R.id.host_container, fragment)
            .addToBackStack("")
            .commit()
    }

}
