package com.example.nasa_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nasa_app.databinding.FavoritesItemBinding
import com.example.nasa_app.model.PODModel

class FavoritesAdapter(
    val onFavoritesClickedListener: OnFavoritesCheckboxListener?
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favorites: List<PODModel> = listOf()

    fun setFavoritesList(data: List<PODModel>) {
        favorites = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class FavoritesViewHolder(private val binding: FavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: PODModel) {
            with(binding) {
                favoritesPic.load(favorites.url)
                favoritesTitle.text = favorites.title
                if (favorites.copyright != "null") {
                    favoritesCopyright.text = favorites.copyright
                } else {
                    favoritesCopyright.text = ""
                }

                // click listener to remove from favorites
                favoritesCheckbox.run {
                    setOnClickListener {
                        onFavoritesClickedListener?.onItemChecked(this, favorites)
                    }
                }
            }
        }
    }

    interface OnFavoritesCheckboxListener {
        fun onItemChecked(p0: View, favorites: PODModel)
    }
}


