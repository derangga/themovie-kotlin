package com.themovie.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themovie.R
import com.themovie.model.local.Trending
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_trending.view.*

class TrendingAdapter : ListAdapter<Trending, TrendingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Trending> = object: DiffUtil.ItemCallback<Trending>(){
            override fun areItemsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.title == newItem.title && oldItem.imgPath == newItem.imgPath
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trending = getItem(position)
        holder.itemView.tr_title.text = trending.title
        val urlImg = ApiUrl.IMG_BACK + trending.imgPath
        Glide.with(context)
            .load(urlImg)
            .error(R.drawable.no_image)
            .into(holder.itemView.tr_bg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_trending, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}