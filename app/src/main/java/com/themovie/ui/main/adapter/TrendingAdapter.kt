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
import com.themovie.model.local.Trending
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.item_header_main.view.*

class TrendingAdapter : ListAdapter<Trending, TrendingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Trending> = object: DiffUtil.ItemCallback<Trending>(){
            override fun areItemsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.title == newItem.title && oldItem.backDropPath == newItem.backDropPath
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header_main, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trending = getItem(position)

        val urlImg = ApiUrl.IMG_BACK + trending.backDropPath
        ImageCache.setImageViewUrl(context, urlImg, holder.itemView.v_img)
        holder.itemView.v_title.text = trending.title
        holder.itemView.v_card.setOnClickListener {
            onClickAdapterListener.onClick(it, trending, holder.itemView.v_img)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnClickAdapterListener {
        fun onClick(view: View?, trending: Trending, imageViewRes: ImageView)
    }
}