package com.themovie.ui.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.person.Filmography
import com.themovie.databinding.AdapterRecomendedBinding
import com.themovie.helper.customview.PortraitView

class PersonFilmAdapter(
    private val onItemClick: (Filmography) -> Unit
) : ListAdapter<Filmography, PersonFilmAdapter.ViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterRecomendedBinding.inflate(inflater, parent, false)
        return ViewHolder(view.root, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View, private val binding: AdapterRecomendedBinding) : RecyclerView.ViewHolder(itemView){
        fun bindItem(item: Filmography){
            val imgUrl = "${ApiUrl.IMG_POSTER}${item.posterPath.toString()}"
            binding.recItem.apply {
                setImage(imgUrl)
                setTitle(item.title.orEmpty())
                setRating(item.rating.orEmpty())
                setOnClickListener(object: PortraitView.OnClickListener{
                    override fun onClick() {
                        onItemClick.invoke(item)
                    }
                })
            }
        }
    }
}