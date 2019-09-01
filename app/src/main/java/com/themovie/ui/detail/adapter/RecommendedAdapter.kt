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
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_recomended.view.*

class RecommendedAdapter : ListAdapter<Movies, RecommendedAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

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

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recomended, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = getItem(position)
        val imgUrl = ApiUrl.IMG_POSTER + movies.posterPath.toString()
        holder.itemView.recom_title.text = movies.title
        ImageCache.setImageViewUrl(context, imgUrl, holder.itemView.recom_img)
        holder.itemView.recom_card.setOnClickListener {
            onClickAdapterListener.onClick(it, movies)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnClickAdapterListener {
        fun onClick(view: View?, movies: Movies)
    }
}