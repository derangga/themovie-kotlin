package com.themovie.ui.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.ArtistFilm
import com.themovie.databinding.AdapterRecomendedBinding
import com.themovie.helper.customview.PortraitView

class PersonFilmAdapter(
    private val onItemClick: (ArtistFilm) -> Unit
) : ListAdapter<ArtistFilm, PersonFilmAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArtistFilm> = object: DiffUtil.ItemCallback<ArtistFilm>(){
            override fun areItemsTheSame(oldItem: ArtistFilm, newItem: ArtistFilm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArtistFilm, newItem: ArtistFilm): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterRecomendedBinding.inflate(inflater, parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View, private val binding: AdapterRecomendedBinding) : RecyclerView.ViewHolder(itemView){
        fun bindItem(item: ArtistFilm){
            binding.recItem.image("${ApiUrl.IMG_POSTER}${item.posterPath}")
                .title(item.title)
                .rating(item.rating)
                .setOnClickListener { onItemClick.invoke(item) }
        }
    }
}