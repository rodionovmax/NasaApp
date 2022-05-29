package com.example.nasa_app.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.nasa_app.R
import com.example.nasa_app.ThemeListener
import com.example.nasa_app.databinding.FragmentSettingsBinding
import com.example.nasa_app.ui.MainActivity
import com.example.nasa_app.util.RADIO_BTN_THEME
import com.example.nasa_app.util.THEME
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment() : Fragment() {

    private lateinit var themeListener: ThemeListener

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val intent = Intent(requireContext(), MainActivity::class.java)

        // check the radio button based on the theme saved in shared preferences
        checkSelectedTheme()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_button_1 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Space)
                        putInt(RADIO_BTN_THEME, 0)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Space)
                }
                R.id.radio_button_2 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Moon)
                        putInt(RADIO_BTN_THEME, 1)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Moon)
                }
                R.id.radio_button_3 -> {
                    sharedPref ?: return@setOnCheckedChangeListener
                    with (sharedPref.edit()) {
                        putInt(THEME, R.style.Theme_Mars)
                        putInt(RADIO_BTN_THEME, 2)
                        apply()
                    }
                    themeListener.onThemeSelected(R.style.Theme_Mars)
                }
            }
            startActivity(intent)
        }
    }

    private fun checkSelectedTheme() {
        val sharedPref = activity?.getPreferences(MODE_PRIVATE)
        val savedRadioIndex = sharedPref?.getInt(RADIO_BTN_THEME, 0)
        val savedCheckedRadioButton: RadioButton = savedRadioIndex?.let { binding.radioGroup.getChildAt(it) } as RadioButton
        savedCheckedRadioButton.isChecked = true
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}