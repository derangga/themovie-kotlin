package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.ImageCache
import com.themovie.model.local.Upcoming
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_upcoming.view.*

class UpcomingAdapter : ListAdapter<Upcoming, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Upcoming> = object: DiffUtil.ItemCallback<Upcoming>(){
            override fun areItemsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.mvId == newItem.mvId
            }

            override fun areContentsTheSame(oldItem: Upcoming, newItem: Upcoming): Boolean {
                return oldItem.title == newItem.title && oldItem.backDropPath == newItem.backDropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_upcoming, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(upcoming: Upcoming){
            itemView.apply {
                val backImg = "${ApiUrl.IMG_BACK}${upcoming.backDropPath}"
                val posterImg = "${ApiUrl.IMG_POSTER}${upcoming.posterPath}"

                ImageCache.setRoundedImageUrl(context, posterImg, mupco_poster)
                ImageCache.setImageViewUrl(context, backImg, mupco_background)
                mupco_title.text = upcoming.title
                mupco_date.text = upcoming.dateRelease
                mupco_card.setOnClickListener {
                    onClickAdapterListener.onClick(it, upcoming, mupco_background)
                }
            }
        }
    }

    interface OnClickAdapterListener {
        fun onClick(view: View?, upcoming: Upcoming, imageViewRes: ImageView)
    }
}