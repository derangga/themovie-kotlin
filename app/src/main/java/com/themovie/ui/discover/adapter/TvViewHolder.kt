package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.cacheImage
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_tv.view.*

class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(context: Context, tv: Tv, onItemClickListener: OnAdapterListener<Tv>){
        val posterUrl = ApiUrl.IMG_POSTER + tv.posterPath.toString()
        val backUrl = ApiUrl.IMG_BACK + tv.backdropPath.toString()

        cacheImage(context, backUrl, itemView.tv_background)
        cacheImage(context, posterUrl, itemView.tv_poster)
        itemView.tv_title.text = tv.name
        itemView.tv_descript.text = tv.overview
        itemView.tv_rate.text = tv.voteAverage
        itemView.tv_item.setOnClickListener {
            onItemClickListener.onClick(it, tv)
        }
    }
}