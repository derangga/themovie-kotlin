package com.themovie.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.databinding.AdapterCreditsBinding
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView
import com.themovie.model.online.detail.Credits
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_credits.view.*

class CreditsAdapter : ListAdapter<Credits, CreditsAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onClickAdapterListener: OnAdapterListener<Credits>

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

    fun setOnClickListener(onClickAdapterListener: OnAdapterListener<Credits>){
        this.onClickAdapterListener = onClickAdapterListener
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
                        onClickAdapterListener.onClick(itemView, credits)
                    }
                })
            }
        }
    }
}