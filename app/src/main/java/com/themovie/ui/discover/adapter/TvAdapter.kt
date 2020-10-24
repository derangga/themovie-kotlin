package com.themovie.ui.discover.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.TvResponse
import com.themovie.databinding.AdapterTvBinding

class TvAdapter (
    private val onItemClick: (TvResponse) -> Unit
): PagingDataAdapter<TvResponse, TvAdapter.TvViewHolder>(COMPARATOR) {

    companion object{
        val COMPARATOR: DiffUtil.ItemCallback<TvResponse> = object: DiffUtil.ItemCallback<TvResponse>(){
            override fun areItemsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterTvBinding
            .inflate(inflater, parent, false)
        return TvViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.binding.vh = holder
        holder.binding.tv = getItem(position)
    }

    inner class TvViewHolder(root: View, val binding: AdapterTvBinding) : RecyclerView.ViewHolder(root) {
        fun onTvAdapterClick(tv: TvResponse){
            onItemClick.invoke(tv)
        }
    }
}