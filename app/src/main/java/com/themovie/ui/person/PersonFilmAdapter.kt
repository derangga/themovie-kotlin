package com.themovie.ui.person

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.person.Filmography
import com.themovie.R
import com.themovie.helper.OnAdapterListener
import com.themovie.helper.customview.PortraitView
import kotlinx.android.synthetic.main.adapter_recomended.view.*

class PersonFilmAdapter : ListAdapter<Filmography, PersonFilmAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context
    private lateinit var onClickItemListener: OnAdapterListener<Filmography>

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Filmography> = object: DiffUtil.ItemCallback<Filmography>(){
            override fun areItemsTheSame(oldItem: Filmography, newItem: Filmography): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Filmography, newItem: Filmography): Boolean {
                return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
            }
        }
    }

    fun setOnItemCLickListener(onClickItemListener: OnAdapterListener<Filmography>){
        this.onClickItemListener = onClickItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recomended, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(item: Filmography){
            itemView.apply {
                val imgUrl = "${ApiUrl.IMG_POSTER}${item.posterPath.toString()}"
                rec_item.apply {
                    setImage(imgUrl)
                    setTitle(item.title.orEmpty())
                    setRating(item.rating.orEmpty())
                    setOnClickListener(object: PortraitView.OnClickListener{
                        override fun onClick() {
                            onClickItemListener.onClick(itemView, item)
                        }
                    })
                }

            }
        }
    }
}