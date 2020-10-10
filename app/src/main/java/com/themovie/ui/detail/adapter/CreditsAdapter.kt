package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.Credits
import com.themovie.databinding.AdapterCreditsBinding
import com.themovie.helper.customview.PortraitView

class CreditsAdapter (
    private val onItemClick: (Credits) -> Unit
) : ListAdapter<Credits, CreditsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Credits> = object: DiffUtil.ItemCallback<Credits>(){
            override fun areItemsTheSame(oldItem: Credits, newItem: Credits): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Credits, newItem: Credits): Boolean {
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
        fun bindItem(credits: Credits){
            val imgUrl = "${ApiUrl.IMG_POSTER}${credits.profilePath.toString()}"
            binding.castPortrait.apply {
                setTitle(credits.name.orEmpty())
                setSubtitle(credits.character.orEmpty())
                setImage(imgUrl)
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onItemClick.invoke(credits)
                    }
                })
            }
        }
    }
}