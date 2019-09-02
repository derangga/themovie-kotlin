package com.themovie.ui.person

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.ImageCache
import com.themovie.model.online.person.Filmography
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_recomended.view.*

class PersonFilmAdapter : ListAdapter<Filmography, PersonFilmAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Filmography> = object: DiffUtil.ItemCallback<Filmography>(){
            override fun areItemsTheSame(oldItem: Filmography, newItem: Filmography): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Filmography, newItem: Filmography): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recomended, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = getItem(position)
        val imgUrl = ApiUrl.IMG_POSTER + film.posterPath.toString()
        holder.itemView.recom_title.text = film.title
        ImageCache.setImageViewUrl(context, imgUrl, holder.itemView.recom_img)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}