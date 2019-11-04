package com.themovie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.model.local.GenreLocal
import kotlinx.android.synthetic.main.adapter_genre.view.*

class GenreAdapter : ListAdapter<GenreLocal, GenreAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var listener: OnClickAdapterListener

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GenreLocal> = object: DiffUtil.ItemCallback<GenreLocal>(){
            override fun areItemsTheSame(oldItem: GenreLocal, newItem: GenreLocal): Boolean {
                return oldItem.genreId == newItem.genreId
            }

            override fun areContentsTheSame(oldItem: GenreLocal, newItem: GenreLocal): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_genre, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: GenreLocal){
            itemView.apply {
                genre_name.text = item.name
            }
            itemView.setOnClickListener { listener.itemGenreClick(itemView, item) }
        }
    }

    fun setGenreClickListener(listener: OnClickAdapterListener){
        this.listener = listener
    }

    interface OnClickAdapterListener {
        fun itemGenreClick(view: View, genreLocal: GenreLocal)
    }
}