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
import com.example.nasa_app.databinding.FragmentAboutBinding
import com.example.nasa_app.databinding.FragmentChipsBinding
import com.example.nasa_app.databinding.FragmentFavoritesBinding
import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.ui.AppState
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(), FavoritesAdapter.OnFavoritesCheckboxListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoritesRecycler.adapter = adapter
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getFavoritesFromViewModel()
    }

    private fun getFavoritesFromViewModel() {
        viewModel.liveDataToObserve.observe(viewLifecycleOwner) {renderData(it)}
        viewModel.getFavoritePictures()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.favoritesLoadingLayout.visibility = View.GONE
                adapter.setFavoritesList(appState.success as List<PODModel>)
            }
            is AppState.Loading -> {
                binding.favoritesLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                binding.favoritesLoadingLayout.visibility = View.GONE
                Toast.makeText(requireContext(), "Oops something went wrong with loading favorites list...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemChecked(p0: View, favorites: PODModel) {
        p0 as CheckBox
        val isChecked: Boolean = p0.isChecked
        when (p0.id) {
            R.id.favoritesCheckbox -> if (isChecked) {
                removeFromFavorites(favorites)
                Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromFavorites(picture: PODModel) {
        viewModel.removePictureFromFavorites(picture)
    }

    private fun renderDataToRemoveFromFavorites(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.favoritesLoadingLayout.visibility = View.GONE
                getFavoritesFromViewModel()
            }
            is AppState.Loading -> {
                binding.favoritesLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                binding.favoritesLoadingLayout.visibility = View.GONE
                Toast.makeText(requireContext(), "Oops something went wrong with loading favorites list...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

}