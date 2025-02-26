package com.themovie.tmdb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.ui.Movie
import com.themovie.tmdb.databinding.AdapterPortraitUpcomingBinding
import com.themovie.tmdb.helper.convertDate

class UpcomingAdapter (
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterPortraitUpcomingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterPortraitUpcomingBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(upcoming: Movie){
            binding.upcomingItem.image("${ApiUrl.IMG_POSTER}${upcoming.posterPath}")
                .title(upcoming.title)
                .dateRelease(upcoming.releaseDate.convertDate())
                .setOnClickListener { onItemClick.invoke(upcoming) }
        }
    }
}