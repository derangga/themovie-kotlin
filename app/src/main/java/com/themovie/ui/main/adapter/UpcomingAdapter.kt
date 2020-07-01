package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterPortraitUpcomingBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.convertDate
import com.themovie.helper.customview.PortraitView
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiUrl

class UpcomingAdapter : ListAdapter<Upcoming, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var listener: OnAdapterListener<Upcoming>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Upcoming> = object: DiffUtil.ItemCallback<Upcoming>(){
            override fun areItemsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(listener: OnAdapterListener<Upcoming>){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = AdapterPortraitUpcomingBinding
            .inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterPortraitUpcomingBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(upcoming: Upcoming){
            val posterImg = "${ApiUrl.IMG_POSTER}${upcoming.posterPath}"
            binding.upcomingItem.apply {
                setImage(posterImg)
                setTitle(upcoming.title.orEmpty())
                setDateRelease(upcoming.releaseDate.convertDate())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        listener.onClick(itemView, upcoming)
                    }
                })
            }
        }
    }
}