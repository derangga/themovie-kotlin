package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterRecomendedBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiUrl

class RecommendedTvAdapter : ListAdapter<Tv, RecommendedTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnAdapterListener<Tv>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tv> = object: DiffUtil.ItemCallback<Tv>(){
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.name == newItem.name && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<Tv>){
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
        fun bindItem(tv: Tv){
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