package com.themovie.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.MovieResponse
import com.themovie.databinding.AdapterMoviesBinding

class MovieAdapter (
    private val onItemClick: (MovieResponse) -> Unit
): PagingDataAdapter<MovieResponse, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object: DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
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
        fun onMovieClick (element: MovieResponse) {
            onItemClick.invoke(element)
        }
    }
}