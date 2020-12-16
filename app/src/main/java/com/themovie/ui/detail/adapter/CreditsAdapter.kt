package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ui.Credit
import com.themovie.databinding.AdapterCreditsBinding
import com.themovie.helper.customview.PortraitView

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
        fun bindItem(Credit: Credit){
            val imgUrl = "${ApiUrl.IMG_POSTER}${Credit.profilePath.toString()}"
            binding.castPortrait.apply {
                setTitle(Credit.name)
                setSubtitle(Credit.character)
                setImage(imgUrl)
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onItemClick.invoke(Credit)
                    }
                })
            }
        }
    }
}