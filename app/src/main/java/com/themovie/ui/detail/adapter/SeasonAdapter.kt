package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.portraintview.PortraitView
import com.themovie.model.online.detail.SeasonTv
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_season.view.*

class SeasonAdapter : ListAdapter<SeasonTv, SeasonAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SeasonTv> = object: DiffUtil.ItemCallback<SeasonTv>(){
            override fun areItemsTheSame(oldItem: SeasonTv, newItem: SeasonTv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SeasonTv, newItem: SeasonTv): Boolean {
                return oldItem.name == newItem.name&& oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_season, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(season: SeasonTv){
            itemView.apply {
                val imgUrl = "${ApiUrl.IMG_POSTER}${season.posterPath.toString()}"
                season_item.apply {
                    setImage(imgUrl)
                    setTitle(season.name)
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {

                        }
                    })
                }
            }
        }
    }
}