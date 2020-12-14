package com.themovie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.Tv
import com.themovie.databinding.AdapterPortraitTvBinding
import com.themovie.helper.customview.PortraitView

class DiscoverTvAdapter (
    private val onItemClick: (Tv) -> Unit
): ListAdapter<Tv, DiscoverTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tv> = object: DiffUtil.ItemCallback<Tv>(){
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.name == newItem.name && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterPortraitTvBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


    inner class ViewHolder(root: View, val binding: AdapterPortraitTvBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(tvLocal: Tv){
            val imgPoster = "${ApiUrl.IMG_POSTER}${tvLocal.posterPath}"
            binding.tvItem.apply {
                setTitle(tvLocal.name)
                setImage(imgPoster)
                setRating(tvLocal.voteAverage)
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onItemClick.invoke(tvLocal)
                    }
                })
            }
        }
    }
}