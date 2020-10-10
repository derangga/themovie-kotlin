package com.themovie.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.MovieResponse
import com.themovie.databinding.AdapterSuggestBinding

class SuggestMoviesAdapter (
    private val onItemClick: (MovieResponse) -> Unit
): ListAdapter<MovieResponse, SuggestMoviesAdapter.ViewHolder>(DIFF_CALLBACK){

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieResponse> = object: DiffUtil.ItemCallback<MovieResponse>(){
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterSuggestBinding.inflate(inflater, parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View, private val binding: AdapterSuggestBinding) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: MovieResponse){
            binding.tvSuggest.text = item.title
            binding.suggest.setOnClickListener { onItemClick.invoke(item) }
        }
    }

}