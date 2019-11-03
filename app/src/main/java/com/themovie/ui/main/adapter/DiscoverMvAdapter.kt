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
import com.themovie.helper.portraintview.PortraitView
import com.themovie.model.local.MoviesLocal
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_maindmv.view.*

class DiscoverMvAdapter : ListAdapter<MoviesLocal, DiscoverMvAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var listener: OnClickAdapterListener

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
        this.listener = onClickAdapterListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(movies: MoviesLocal){
            itemView.apply {
                val imgPoster = "${ApiUrl.IMG_POSTER}${movies.posterPath}"
                movie_item.apply {
                    setImage(imgPoster)
                    setTitle(movies.title)
                    setRating(movies.rating)
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            listener.onClick(itemView, movies)
                        }
                    })
                }
            }
        }
    }

    interface OnClickAdapterListener {
        fun onClick(view: View?, moviesLocal: MoviesLocal)
    }
}