package com.example.nasa_app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa_app.R
import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.ui.AppState
import com.example.nasa_app.util.showToast
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(), FavoritesAdapter.OnFavoritesCheckboxListener {

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
    }
    private val adapter = FavoritesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesRecycler.adapter = adapter
        favoritesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getFavoritesFromViewModel()
    }

    private fun getFavoritesFromViewModel() {
        viewModel.liveDataToObserve.observe(viewLifecycleOwner) {renderData(it)}
        viewModel.getFavoritePictures()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                favoritesLoadingLayout.visibility = View.GONE
                adapter.setFavoritesList(appState.success as List<PODModel>)
            }
            is AppState.Loading -> {
                favoritesLoadingLayout.visibility = View.VISIBLE
                showToast(requireContext(), "Loading...")
            }
            is AppState.Error -> {
                favoritesLoadingLayout.visibility = View.GONE
                showToast(requireContext(), "Oops something went wrong with loading favorites list...")
            }
        }
    }

    override fun onItemChecked(p0: View, favorites: PODModel) {
        p0 as CheckBox
        val isChecked: Boolean = p0.isChecked
        when (p0.id) {
            R.id.favoritesCheckbox -> if (isChecked) {
                removeFromFavorites(favorites)
                showToast(requireContext(), "Removed from Favorites")
            }
        }
    }

    private fun removeFromFavorites(picture: PODModel) {
        viewModel.removePictureFromFavorites(picture)
    }

    private fun renderDataToRemoveFromFavorites(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                favoritesLoadingLayout.visibility = View.GONE
                getFavoritesFromViewModel()
            }
            is AppState.Loading -> {
                favoritesLoadingLayout.visibility = View.VISIBLE
                showToast(requireContext(), "Loading...")
            }
            is AppState.Error -> {
                favoritesLoadingLayout.visibility = View.GONE
                showToast(requireContext(), "Oops something went wrong with loading favorites list...")
            }
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

}