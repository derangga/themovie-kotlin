package com.themovie.tmdb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.ui.Movie
import com.themovie.tmdb.databinding.AdapterPortraitMovieBinding
import com.themovie.tmdb.helper.customview.PortraitView

class DiscoverMovieAdapter (
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, DiscoverMovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterPortraitMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterPortraitMovieBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(movies: Movie){
            binding.movieItem.image("${ApiUrl.IMG_POSTER}${movies.posterPath}")
                .title(movies.title)
                .rating(movies.voteAverage)
                .setOnClickListener { onItemClick.invoke(movies) }
        }
    }
}