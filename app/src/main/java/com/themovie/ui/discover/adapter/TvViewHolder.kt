package com.themovie.ui.discover.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.themovie.helper.ImageCache
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_tv.view.*

class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(context: Context, tv: Tv, onItemClickListener: TvAdapter.OnClickAdapterListener){
        val posterUrl = ApiUrl.IMG_POSTER + tv.posterPath.toString()
        val backUrl = ApiUrl.IMG_BACK + tv.backdropPath.toString()

        ImageCache.setImageViewUrl(context, backUrl, itemView.tv_background)
        ImageCache.setRoundedImageUrl(context, posterUrl, itemView.tv_poster)
        itemView.tv_title.text = tv.name
        itemView.tv_descript.text = tv.overview
        itemView.tv_rate.text = tv.voteAverage
        itemView.tv_item.setOnClickListener {
            onItemClickListener.onItemClick(it, tv, itemView.tv_background)
        }
    }
}