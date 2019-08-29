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
import com.themovie.model.local.Upcoming
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_upcoming.view.*

class UpcomingAdapter : ListAdapter<Upcoming, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Upcoming> = object: DiffUtil.ItemCallback<Upcoming>(){
            override fun areItemsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.mvId == newItem.mvId
            }

            override fun areContentsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.title == newItem.title && oldItem.imgPath == newItem.imgPath
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_upcoming, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upcoming = getItem(position)
        holder.itemView.up_title.text = upcoming.title
        holder.itemView.up_release.text = upcoming.dateRelease
        val urlImg = ApiUrl.IMG_BACK + upcoming.imgPath
        Glide.with(context)
            .load(urlImg)
            .error(R.drawable.no_image)
            .into(holder.itemView.up_bg)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}