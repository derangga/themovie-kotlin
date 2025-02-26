package com.themovie.tmdb.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.datasource.entities.ui.Movie
import com.themovie.tmdb.databinding.AdapterMoviesBinding

class MovieAdapter (
    private val onItemClick: (Movie) -> Unit
): PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterMoviesBinding
            .inflate(inflater, parent, false)
        return MovieViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.vh = holder
        holder.binding.movie = getItem(position)
    }

    inner class MovieViewHolder(
        itemView: View,
        val binding: AdapterMoviesBinding
    ) : RecyclerView.ViewHolder(itemView) {
        fun onMovieClick (element: Movie) {
            onItemClick.invoke(element)
        }
    }
}