package com.themovie.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.domain.entities.ui.Tv
import com.themovie.databinding.AdapterSuggestBinding

class SuggestTvAdapter (
    private val onItemClick: (Tv) -> Unit
): ListAdapter<Tv, SuggestTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tv> = object: DiffUtil.ItemCallback<Tv>(){
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
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
        fun bind(item: Tv) {
            binding.tvSuggest.text = item.name
            binding.suggest.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}