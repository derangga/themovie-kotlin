package com.themovie.ui.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themovie.R
import com.themovie.helper.portraintview.PortraitView
import com.themovie.model.online.detail.Credits
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_credits.view.*

class CreditsAdapter : ListAdapter<Credits, CreditsAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickAdapterListener: OnClickAdapterListener

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

    fun setOnClickListener(onClickAdapterListener: OnClickAdapterListener){
        this.onClickAdapterListener = onClickAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_credits, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    interface OnClickAdapterListener {
        fun onClick(credits: Credits)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(credits: Credits){
            itemView.apply {
                val imgUrl = "${ApiUrl.IMG_POSTER}${credits.profilePath.toString()}"
                cast_portrait.apply {
                    setTitle(credits.name)
                    setSubtitle(credits.character)
                    setImage(imgUrl)
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            onClickAdapterListener.onClick(credits)
                        }
                    })
                }

            }

        }
    }
}