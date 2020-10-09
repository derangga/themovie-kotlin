package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.MovieResponse
import com.themovie.databinding.AdapterRecomendedBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView

class RecommendedAdapter : ListAdapter<MovieResponse, RecommendedAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnAdapterListener<MovieResponse>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieResponse> = object: DiffUtil.ItemCallback<MovieResponse>(){
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<MovieResponse>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterRecomendedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterRecomendedBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(movies: MovieResponse){
            val imgUrl = "${ApiUrl.IMG_POSTER}${movies.posterPath.toString()}"
            binding.recItem.apply {
                setImage(imgUrl)
                setTitle(movies.title.orEmpty())
                setRating(movies.voteAverage.orEmpty())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onClickAdapterListener.onClick(itemView, movies)
                    }
                })
            }
        }
    }
}