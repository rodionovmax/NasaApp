package com.example.nasa_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nasa_app.R
import com.example.nasa_app.databinding.FavoritesItemBinding
import com.example.nasa_app.databinding.FragmentChipsBinding
import com.example.nasa_app.databinding.FragmentFavoritesBinding
import com.example.nasa_app.network.models.PODModel
import kotlinx.android.synthetic.main.favorites_item.view.*

class FavoritesAdapter(
    val onFavoritesClickedListener: OnFavoritesCheckboxListener?
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

//    private var _binding: FavoritesItemBinding? = null
//    private val binding get() = _binding!!

    private var favorites: List<PODModel> = listOf()

    fun setFavoritesList(data: List<PODModel>) {
        favorites = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
//        val binding = HoursListItemsBinding
//            .inflate(LayoutInflater.from(parent.context), parent, false)
//        return HoursViewHolder(binding)
        val binding = FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
//        return FavoritesViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.favorites_item, parent, false) as View
//        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class FavoritesViewHolder(private val binding: FavoritesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: PODModel) {
            with(binding) {
                favoritesPic.load(favorites.url)
                favoritesTitle.text = favorites.title
                favoritesCopyright.text = favorites.copyright

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


