package com.themovie.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.local.TrendingEntity
import com.themovie.databinding.ItemHeaderMainBinding

class TrendingAdapter (
    private val onItemClick: (TrendingEntity) -> Unit,
    private val onItemTouch: (motionEvent: MotionEvent?) -> Unit
) : ListAdapter<TrendingEntity, TrendingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TrendingEntity> = object: DiffUtil.ItemCallback<TrendingEntity>(){
            override fun areItemsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TrendingEntity, newItem: TrendingEntity): Boolean {
                return oldItem.title == newItem.title && oldItem.backdropPath == newItem.backdropPath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHeaderMainBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view.root, view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.trend = getItem(position)
        holder.binding.vh = holder
        holder.binding.vCard.setOnTouchListener { _ , event ->
            onItemTouch.invoke(event)
            false
        }
    }

    inner class ViewHolder(root: View, val binding: ItemHeaderMainBinding) : RecyclerView.ViewHolder(root) {
        fun trendingClick(trending: TrendingEntity){
            onItemClick.invoke(trending)
        }
    }
}