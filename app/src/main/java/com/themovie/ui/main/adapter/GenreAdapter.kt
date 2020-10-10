package com.themovie.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.local.GenreEntity
import com.themovie.databinding.AdapterGenreBinding

class GenreAdapter (
    private val onItemClick: (GenreEntity) -> Unit
): ListAdapter<GenreEntity, GenreAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GenreEntity> = object: DiffUtil.ItemCallback<GenreEntity>(){
            override fun areItemsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GenreEntity, newItem: GenreEntity): Boolean {
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
        fun genreClick(genre: GenreEntity){
            onItemClick.invoke(genre)
        }
    }
}