package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.ImageCache
import com.themovie.helper.OnAdapterListener
import com.themovie.model.online.video.Videos
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_video.view.*

class VideoAdapter : ListAdapter<Videos, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
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
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(video: Videos){
            itemView.apply {
                val urlThumbnail = ApiUrl.THUMBNAIL.replace("key", video.key)
                ImageCache.setImageViewUrl(context, urlThumbnail, t_thumbnail)
                t_title.text = video.name
                t_layout.setOnClickListener {
                    listener.onClick(itemView, video)
                }
            }
        }
    }
}