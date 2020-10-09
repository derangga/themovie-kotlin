package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.themovie.databinding.AdapterPortraitUpcomingBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.convertDate
import com.themovie.helper.customview.PortraitView

class UpcomingAdapter : ListAdapter<UpcomingEntity, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var listener: OnAdapterListener<UpcomingEntity>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UpcomingEntity> = object: DiffUtil.ItemCallback<UpcomingEntity>(){
            override fun areItemsTheSame(oldItem: UpcomingEntity, newItem: UpcomingEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UpcomingEntity, newItem: UpcomingEntity): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(listener: OnAdapterListener<UpcomingEntity>){
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
        fun bindItem(upcoming: UpcomingEntity){
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