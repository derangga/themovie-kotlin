package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.OnAdapterListener
import com.themovie.model.online.detail.Reviews
import kotlinx.android.synthetic.main.adapter_review.view.*

class ReviewsAdapter : ListAdapter<Reviews, ReviewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnAdapterListener<Reviews>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Reviews> = object: DiffUtil.ItemCallback<Reviews>(){
            override fun areItemsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Reviews, newItem: Reviews): Boolean {
                return oldItem.author == newItem.author && oldItem.content == newItem.content
            }
        }
    }

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<Reviews>){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_review, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(review: Reviews){
            itemView.apply {
                rev_author.text = review.author
                rev_content.text = review.content
                rev_card.setOnClickListener {
                    onClickAdapterListener.onClick(it, review)
                }
            }
        }
    }
}