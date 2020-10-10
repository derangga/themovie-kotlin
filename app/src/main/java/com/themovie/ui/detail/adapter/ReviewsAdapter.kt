package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.ReviewsResponse
import com.themovie.databinding.AdapterReviewBinding

class ReviewsAdapter(
    private val onItemClick: (ReviewsResponse) -> Unit
) : ListAdapter<ReviewsResponse, ReviewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ReviewsResponse> = object: DiffUtil.ItemCallback<ReviewsResponse>(){
            override fun areItemsTheSame(oldItem: ReviewsResponse, newItem: ReviewsResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReviewsResponse, newItem: ReviewsResponse): Boolean {
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
        fun onReviewClick(data: ReviewsResponse){
            onItemClick.invoke(data)
        }
    }
}