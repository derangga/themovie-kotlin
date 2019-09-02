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
import com.themovie.model.online.video.Videos
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_video.view.*

class VideoAdapter : ListAdapter<Videos, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

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
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_video, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        val urlThumbnail = ApiUrl.THUMBNAIL.replace("key", video.key)
        ImageCache.setImageViewUrl(context, urlThumbnail, holder.itemView.t_thumbnail)
        holder.itemView.t_title.text = video.name
        holder.itemView.t_type.text = video.type
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}