package com.themovie.tmdb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.datasource.entities.ui.Review
import com.themovie.tmdb.databinding.AdapterReviewBinding

class ReviewsAdapter(
    private val onItemClick: (Review) -> Unit
) : ListAdapter<Review, ReviewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Review> = object: DiffUtil.ItemCallback<Review>(){
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.author == newItem.author && oldItem.content == newItem.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterReviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.review = getItem(position)
        holder.binding.vh = holder
    }


    inner class ViewHolder(root: View, val binding: AdapterReviewBinding) : RecyclerView.ViewHolder(root){
        fun onReviewClick(data: Review){
            onItemClick.invoke(data)
        }
    }
}