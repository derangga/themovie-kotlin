package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.portraintview.PortraitView
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_recomended.view.*

class RecommendedAdapter : ListAdapter<Movies, RecommendedAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnAdapterListener<Movies>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movies> = object: DiffUtil.ItemCallback<Movies>(){
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<Movies>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recomended, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(movies: Movies){
            itemView.apply {
                val imgUrl = "${ApiUrl.IMG_POSTER}${movies.posterPath.toString()}"
                rec_item.apply {
                    setImage(imgUrl)
                    setTitle(movies.title)
                    setRating(movies.voteAverage)
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            onClickAdapterListener.onClick(itemView, movies)
                        }
                    })
                }
            }
        }
    }
}