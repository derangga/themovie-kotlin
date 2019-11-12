package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_maindmv.view.*

class DiscoverMvAdapter : ListAdapter<Movies, DiscoverMvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var listener: OnAdapterListener<Movies>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movies> = object: DiffUtil.ItemCallback<Movies>(){
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_maindmv, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    fun setOnClickListener(listener: OnAdapterListener<Movies>){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(movies: Movies){
            itemView.apply {
                val imgPoster = "${ApiUrl.IMG_POSTER}${movies.posterPath}"
                movie_item.apply {
                    setImage(imgPoster)
                    setTitle(movies.title)
                    setRating(movies.voteAverage)
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            listener.onClick(itemView, movies)
                        }
                    })
                }
            }
        }
    }
}