package com.themovie.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.MovieResponse
import com.themovie.databinding.AdapterUpcomingBinding

class UpcomingAdapter (
    private val onItemClick: (MovieResponse) -> Unit
): PagingDataAdapter<MovieResponse, UpcomingAdapter.UpcomingViewHolder>(COMPARATOR) {

    companion object{
        val COMPARATOR: DiffUtil.ItemCallback<MovieResponse> = object: DiffUtil.ItemCallback<MovieResponse>(){
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.posterPath == newItem.posterPath &&
                        oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterUpcomingBinding
            .inflate(inflater, parent, false)
        return UpcomingViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.binding.vh = holder
        holder.binding.upcoming = getItem(position)
    }

    inner class UpcomingViewHolder(root: View, val binding: AdapterUpcomingBinding) : RecyclerView.ViewHolder(root){
        fun onUpcomingClick(data: MovieResponse){
            onItemClick.invoke(data)
        }
    }
}