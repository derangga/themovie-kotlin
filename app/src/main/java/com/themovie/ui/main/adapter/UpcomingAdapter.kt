package com.themovie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.Movie
import com.themovie.databinding.AdapterPortraitUpcomingBinding
import com.themovie.helper.convertDate
import com.themovie.helper.customview.PortraitView

class UpcomingAdapter (
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object: DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterPortraitUpcomingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterPortraitUpcomingBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(upcoming: Movie){
            val posterImg = "${ApiUrl.IMG_POSTER}${upcoming.posterPath}"
            binding.upcomingItem.apply {
                setImage(posterImg)
                setTitle(upcoming.title)
                setDateRelease(upcoming.releaseDate.convertDate())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onItemClick.invoke(upcoming)
                    }
                })
            }
        }
    }
}