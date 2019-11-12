package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.cacheImage
import com.themovie.helper.convertDate
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_movies.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bindView(context: Context, movies: Movies, onItemClickListener: OnAdapterListener<Movies>){
        val posterUrl = ApiUrl.IMG_POSTER + movies.posterPath.toString()
        val backUrl = ApiUrl.IMG_BACK + movies.backdropPath.toString()

        cacheImage(context, backUrl, itemView.mv_background)
        cacheImage(context, posterUrl, itemView.mv_poster)
        itemView.mv_title.text = movies.title
        itemView.mv_descript.text = movies.overview
        itemView.mv_date.text = movies.releaseDate.convertDate()
        itemView.mv_item.setOnClickListener {
            onItemClickListener.onClick(it, movies)
        }
    }
}