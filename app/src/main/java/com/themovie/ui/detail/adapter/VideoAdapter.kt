package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterVideoBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.model.online.video.Videos

class VideoAdapter : ListAdapter<Videos, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var listener: OnAdapterListener<Videos>

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

    fun setOnClickAdapter(onClickAdapterListener: OnAdapterListener<Videos>){
        this.listener = onClickAdapterListener
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
        fun onVideoTap(view: View, video: Videos){
            listener.onClick(view, video)
        }
    }
}