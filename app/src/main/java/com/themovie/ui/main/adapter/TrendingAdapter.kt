package com.themovie.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.ItemHeaderMainBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.model.db.Trending

class TrendingAdapter : ListAdapter<Trending, TrendingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var clickListener: OnAdapterListener<Trending>
    private lateinit var onTouchAdapterListener: OnTouchAdapterListener

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Trending> = object: DiffUtil.ItemCallback<Trending>(){
            override fun areItemsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Trending, newItem: Trending): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    fun setOnClickListener(clickListener: OnAdapterListener<Trending>){
        this.clickListener = clickListener
    }

    fun setOnTouchListener(onTouchAdapterListener: OnTouchAdapterListener){
        this.onTouchAdapterListener = onTouchAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = ItemHeaderMainBinding
            .inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(view.root, view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.trend = getItem(position)
        holder.binding.vh = holder
        holder.binding.vCard.setOnTouchListener { v, event ->
            onTouchAdapterListener.onTouchItem(v, event)
            false
        }
    }

    inner class ViewHolder(root: View, val binding: ItemHeaderMainBinding) : RecyclerView.ViewHolder(root) {
        fun trendingClick(view: View, trending: Trending){
            clickListener.onClick(view, trending)
        }
    }

    interface OnTouchAdapterListener {
        fun onTouchItem(view: View?, motionEvent: MotionEvent?)
    }
}