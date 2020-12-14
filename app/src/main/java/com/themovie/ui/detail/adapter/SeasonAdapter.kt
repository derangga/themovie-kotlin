package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.SeasonTv
import com.themovie.databinding.AdapterSeasonBinding

class SeasonAdapter : ListAdapter<SeasonTv, SeasonAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SeasonTv> = object: DiffUtil.ItemCallback<SeasonTv>(){
            override fun areItemsTheSame(oldItem: SeasonTv, newItem: SeasonTv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SeasonTv, newItem: SeasonTv): Boolean {
                return oldItem.name == newItem.name&& oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterSeasonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(
        root: View,
        private val binding: AdapterSeasonBinding
    ) : RecyclerView.ViewHolder(root){
        fun bindItem(seasonResponse: SeasonTv){
            val imgUrl = "${ApiUrl.IMG_POSTER}${seasonResponse.posterPath.toString()}"
            binding.seasonItem.apply {
                setImage(imgUrl)
                setTitle(seasonResponse.name)
            }
        }
    }
}