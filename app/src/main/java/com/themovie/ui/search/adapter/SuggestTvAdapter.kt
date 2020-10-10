package com.themovie.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.remote.TvResponse
import com.themovie.databinding.AdapterSuggestBinding

class SuggestTvAdapter (
    private val onItemClick: (TvResponse) -> Unit
): ListAdapter<TvResponse, SuggestTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TvResponse> = object: DiffUtil.ItemCallback<TvResponse>(){
            override fun areItemsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvResponse, newItem: TvResponse): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterSuggestBinding.inflate(inflater, parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View, private val binding: AdapterSuggestBinding) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TvResponse) {
            binding.tvSuggest.text = item.name
            binding.suggest.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}