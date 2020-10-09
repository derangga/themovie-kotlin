package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.TvResponse
import com.themovie.databinding.AdapterRecomendedBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView

class RecommendedTvAdapter : ListAdapter<TvResponse, RecommendedTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnAdapterListener<TvResponse>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TvResponse> = object: DiffUtil.ItemCallback<TvResponse>(){
            override fun areItemsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.name == newItem.name && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<TvResponse>){
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

    inner class ViewHolder(
        itemView: View,
        private val binding: AdapterRecomendedBinding
    ) : RecyclerView.ViewHolder(itemView){
        fun bindItem(tv: TvResponse){
            val imgUrl = "${ApiUrl.IMG_POSTER}${tv.posterPath.toString()}"
            binding.recItem.apply {
                setImage(imgUrl)
                setTitle(tv.name.orEmpty())
                setRating(tv.voteAverage.orEmpty())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onClickAdapterListener.onClick(itemView, tv)
                    }
                })
            }
        }
    }
}