package com.example.nasa_app.ui.explore.mars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.nasa_app.databinding.MarsPhotoItemBinding
import com.example.nasa_app.models.MarsPhotoModel


class MarsPhotosAdapter : RecyclerView.Adapter<MarsPhotosAdapter.MarsPhotosViewHolder>() {

    private var marsPhotos: List<MarsPhotoModel> = listOf()

    fun setMarsPhotoList(data: List<MarsPhotoModel>) {
        marsPhotos = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosAdapter.MarsPhotosViewHolder {
        val binding =
            MarsPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPhotosAdapter.MarsPhotosViewHolder, position: Int) {
        holder.bind(marsPhotos[position])
    }

    // return no more than 10 pictures. TODO: implement paging later
    override fun getItemCount(): Int {
        return if (marsPhotos.size < 10) {
            marsPhotos.size
        } else {
            10
        }
    }

    inner class MarsPhotosViewHolder(private val binding: MarsPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: MarsPhotoModel) {
            with(binding) {
                val imgURL = marsPhoto.imgSrc.replace("http", "https")
                Glide.with(itemView.context).load(imgURL).centerCrop().into(marsRoverPhoto)
            }
        }
    }
}