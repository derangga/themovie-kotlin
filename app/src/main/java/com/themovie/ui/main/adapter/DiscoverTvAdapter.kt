package com.themovie.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.ImageCache
import com.themovie.model.local.TvLocal
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_maindtv.view.*

class DiscoverTvAdapter : ListAdapter<TvLocal, DiscoverTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener
    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TvLocal> = object: DiffUtil.ItemCallback<TvLocal>(){
            override fun areItemsTheSame(oldItem: TvLocal, newItem: TvLocal): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(oldItem: TvLocal, newItem: TvLocal): Boolean {
                return oldItem.title == newItem.title && oldItem.backDropPath == newItem.backDropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_maindtv, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvLocal = getItem(position)
        val imgBack = ApiUrl.IMG_BACK + tvLocal.backDropPath
        val imgPoster = ApiUrl.IMG_POSTER + tvLocal.posterPath
        ImageCache.setImageViewUrl(context, imgBack, holder.itemView.mdtv_background)
        ImageCache.setRoundedImageUrl(context, imgPoster, holder.itemView.mdtv_poster)
        holder.itemView.mdtv_title.text = tvLocal.title
        holder.itemView.mdtv_rate.text = tvLocal.rating

        holder.itemView.mdtv_card.setOnClickListener { view ->
            onClickAdapterListener.onClick(view, tvLocal, holder.itemView.mdtv_background)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnClickAdapterListener {
        fun onClick(view: View?, tvLocal: TvLocal, imageViewRes: ImageView)
    }
}