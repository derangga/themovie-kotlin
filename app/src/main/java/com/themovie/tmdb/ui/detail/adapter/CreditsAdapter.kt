package com.themovie.tmdb.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.ui.Credit
import com.themovie.tmdb.databinding.AdapterCreditsBinding
import com.themovie.tmdb.helper.customview.PortraitView

class CreditsAdapter (
    private val onItemClick: (Credit) -> Unit
) : ListAdapter<Credit, CreditsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Credit> = object: DiffUtil.ItemCallback<Credit>(){
            override fun areItemsTheSame(oldItem: Credit, newItem: Credit): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Credit, newItem: Credit): Boolean {
                return oldItem.name == newItem.name&& oldItem.profilePath == newItem.profilePath
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterCreditsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(root: View, val binding: AdapterCreditsBinding) : RecyclerView.ViewHolder(root){
        fun bindItem(credit: Credit){
            binding.castPortrait.image("${ApiUrl.IMG_POSTER}${credit.profilePath.toString()}")
                .title(credit.name)
                .subtitle(credit.character)
                .setOnClickListener { onItemClick.invoke(credit) }
        }
    }
}