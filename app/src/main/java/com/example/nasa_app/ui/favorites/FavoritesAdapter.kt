package com.example.nasa_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.nasa_app.R
import com.example.nasa_app.network.models.PODModel
import kotlinx.android.synthetic.main.favorites_item.view.*

class FavoritesAdapter(
    val onFavoritesClickedListener: OnFavoritesCheckboxListener?
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favorites: List<PODModel> = listOf()

    fun setFavoritesList(data: List<PODModel>) {
        favorites = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorites_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorites: PODModel) {
            with(itemView) {
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


