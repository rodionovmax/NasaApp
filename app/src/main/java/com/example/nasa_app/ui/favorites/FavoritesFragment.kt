package com.example.nasa_app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa_app.R
import com.example.nasa_app.network.models.PODModel
import com.example.nasa_app.ui.AppState
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
    }
    private val adapter = FavoritesAdapter()

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
                @Suppress("UNCHECKED_CAST")
                adapter.setFavoritesList(appState.success as List<PODModel>)
            }
            is AppState.Loading -> {
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(requireContext(), "Oops something went wrong with loading favorites list...", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        fun newInstance() = FavoritesFragment()
    }


}