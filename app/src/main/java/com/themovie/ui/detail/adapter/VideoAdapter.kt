package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.Videos
import com.themovie.databinding.AdapterVideoBinding

class VideoAdapter (
    private val onItemClick: (Videos) -> Unit
) : ListAdapter<Videos, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Videos> = object: DiffUtil.ItemCallback<Videos>(){
            override fun areItemsTheSame(oldItem: Videos, newItem: Videos): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Videos, newItem: Videos): Boolean {
                return oldItem.name == newItem.name&& oldItem.key == newItem.key
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterVideoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.video = getItem(position)
        holder.binding.vh = holder
    }

    inner class ViewHolder(itemView: View, val binding: AdapterVideoBinding) : RecyclerView.ViewHolder(itemView){
        fun onVideoTap(video: Videos){
            onItemClick.invoke(video)
        }
    }
}