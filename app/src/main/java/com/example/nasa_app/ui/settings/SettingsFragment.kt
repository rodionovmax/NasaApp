package com.example.nasa_app.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasa_app.R
import com.example.nasa_app.databinding.FragmentAboutBinding
import com.example.nasa_app.databinding.FragmentSettingsBinding
import com.example.nasa_app.ui.favorites.FavoritesFragment

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}