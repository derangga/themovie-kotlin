package com.themovie.ui.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.ArtistPict
import com.themovie.databinding.AdapterProfilePictBinding
import com.themovie.helper.cacheImage

class PersonImageAdapter: ListAdapter<ArtistPict, PersonImageAdapter.ViewHolder>(comparator) {

    companion object {
        private val comparator = object: DiffUtil.ItemCallback<ArtistPict>() {
            override fun areItemsTheSame(oldItem: ArtistPict, newItem: ArtistPict): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArtistPict, newItem: ArtistPict): Boolean {
                return oldItem.filePath == newItem.filePath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProfilePictBinding
            .inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = getItem(position)?.let { pict ->
            "${ApiUrl.IMG_BACK}${pict.filePath}"
        }.orEmpty()
        holder.binding.photo.cacheImage(imageUrl)
    }

    class ViewHolder(val binding: AdapterProfilePictBinding) : RecyclerView.ViewHolder(binding.root)
}