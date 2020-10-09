package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.local.MovieEntity
import com.themovie.databinding.AdapterPortraitMovieBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView

class DiscoverMovieAdapter : ListAdapter<MovieEntity, DiscoverMovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var listener: OnAdapterListener<MovieEntity>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieEntity> = object: DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = AdapterPortraitMovieBinding
            .inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(view.root, view)
    }

    fun setOnClickListener(listener: OnAdapterListener<MovieEntity>){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterPortraitMovieBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(movies: MovieEntity){
            val imgPoster = "${ApiUrl.IMG_POSTER}${movies.posterPath}"
            binding.movieItem.apply {
                setImage(imgPoster)
                setTitle(movies.title.orEmpty())
                setRating(movies.voteAverage.orEmpty())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        listener.onClick(itemView, movies)
                    }
                })
            }
        }
    }
}