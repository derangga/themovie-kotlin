package com.themovie.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Movies
import kotlinx.android.synthetic.main.adapter_suggest.view.*

class SuggestMoviesAdapter: ListAdapter<Movies, SuggestMoviesAdapter.ViewHolder>(DIFF_CALLBACK){

    private lateinit var context: Context
    private lateinit var listener: OnAdapterListener<Movies>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movies> = object: DiffUtil.ItemCallback<Movies>(){
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    fun setAdapterListener(listener: OnAdapterListener<Movies>){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_suggest, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: Movies){
            itemView.tv_suggest.text = item.title
            itemView.setOnClickListener { listener.onClick(itemView, item) }
        }
    }

}