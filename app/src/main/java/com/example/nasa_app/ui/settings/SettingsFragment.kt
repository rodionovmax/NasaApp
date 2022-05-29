package com.example.nasa_app.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.ThemeListener
import com.example.nasa_app.ui.MainActivity
import com.example.nasa_app.ui.THEME
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment() : Fragment() {

    private lateinit var themeListener: ThemeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ThemeListener) {
            themeListener = context
        } else {
            throw RuntimeException(requireContext().toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val intent = Intent(requireContext(), MainActivity::class.java)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_1 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Space)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Space)
                }
                R.id.radio_button_2 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Moon)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Moon)
                }
                R.id.radio_button_3 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Mars)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Mars)
                }
            }
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}