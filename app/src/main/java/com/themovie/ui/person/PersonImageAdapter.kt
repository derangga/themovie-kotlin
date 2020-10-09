package com.themovie.ui.person

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.person.PersonImage
import com.themovie.R
import com.themovie.helper.cacheImage
import kotlinx.android.synthetic.main.adapter_profile_pict.view.*

class PersonImageAdapter: RecyclerView.Adapter<PersonImageAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var imageList: List<PersonImage>? = null

    fun setImageList(imageList: List<PersonImage>?){
        this.imageList = imageList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_profile_pict, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = "${ApiUrl.IMG_BACK}${imageList?.get(position)?.filePath}"
        cacheImage(context, imageUrl, holder.itemView.photo)
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}