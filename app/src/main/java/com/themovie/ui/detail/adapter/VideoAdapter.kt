package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.ui.Video
import com.themovie.databinding.AdapterVideoBinding

class VideoAdapter (
    private val onItemClick: (Video) -> Unit
) : ListAdapter<Video, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Video> = object: DiffUtil.ItemCallback<Video>(){
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
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
        fun onVideoTap(video: Video){
            onItemClick.invoke(video)
        }
    }
}