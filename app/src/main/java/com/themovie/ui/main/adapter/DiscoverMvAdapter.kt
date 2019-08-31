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
import com.themovie.model.local.MoviesLocal
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_maindmv.view.*

class DiscoverMvAdapter : ListAdapter<MoviesLocal, DiscoverMvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MoviesLocal> = object: DiffUtil.ItemCallback<MoviesLocal>(){
            override fun areItemsTheSame(oldItem: MoviesLocal, newItem: MoviesLocal): Boolean {
                return oldItem.mvId == newItem.mvId
            }

            override fun areContentsTheSame(oldItem: MoviesLocal, newItem: MoviesLocal): Boolean {
                return oldItem.title == newItem.title && oldItem.backDropPath == newItem.backDropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_maindmv, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = getItem(position)
        val imgBack = ApiUrl.IMG_BACK + movies.backDropPath
        val imgPoster = ApiUrl.IMG_POSTER + movies.posterPath
        ImageCache.setImageViewUrl(context, imgBack, holder.itemView.mdmv_background)
        ImageCache.setRoundedImageUrl(context, imgPoster, holder.itemView.mdmv_poster)
        holder.itemView.mdmv_title.text = movies.title
        holder.itemView.mdmv_date.text = movies.dateRelease

        holder.itemView.mdmv_card.setOnClickListener { view -> onClickAdapterListener.onClick(view, movies, holder.itemView.mdmv_background) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnClickAdapterListener {
        fun onClick(view: View?, moviesLocal: MoviesLocal, imageViewRes: ImageView)
    }
}