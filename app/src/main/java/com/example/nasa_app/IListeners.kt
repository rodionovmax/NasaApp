package com.example.nasa_app

import android.view.View
import androidx.fragment.app.Fragment
import com.example.nasa_app.models.PictureModel

interface ThemeListener {
    fun onThemeSelected(theme: Int)
}

interface FragmentNav {
    fun openFragment(fragment: Fragment)
}

interface OnFavoritesCheckboxListener {
    fun onItemChecked(view: View, picture: PictureModel)
}
