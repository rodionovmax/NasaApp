package com.example.nasa_app

import androidx.fragment.app.Fragment

interface ThemeListener {
    fun onThemeSelected(theme: Int)
}

interface FragmentNav {
    fun openFragment(fragment: Fragment)
}
