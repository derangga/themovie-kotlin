package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.convertDate
import com.themovie.helper.portraintview.PortraitView
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_upcoming.view.*

class UpcomingAdapter : ListAdapter<Upcoming, UpcomingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

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
                val posterImg = "${ApiUrl.IMG_POSTER}${upcoming.posterPath}"
                upcoming_item.apply {
                    setImage(posterImg)
                    setTitle(upcoming.title)
                    setDateRelease(upcoming.releaseDate.convertDate())
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            onClickAdapterListener.onClick(itemView, upcoming)
                        }
                    })
                }
            }
        }
    }

    interface OnClickAdapterListener {
        fun onClick(view: View?, upcoming: Upcoming)
    }
}