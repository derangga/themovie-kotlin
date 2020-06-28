package com.themovie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterGenreBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Genre

class GenreAdapter : ListAdapter<Genre, GenreAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var listener: OnAdapterListener<Genre>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Genre> = object: DiffUtil.ItemCallback<Genre>(){
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterGenreBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.genre = getItem(position)
        holder.binding.vh = holder
    }

    inner class ViewHolder(root: View, val binding: AdapterGenreBinding) : RecyclerView.ViewHolder(root) {
        fun genreClick(view: View, genre: Genre){
            itemView.setOnClickListener { listener.onClick(view, genre) }
        }
    }

    fun setGenreClickListener(listener: OnAdapterListener<Genre>){
        this.listener = listener
    }
}