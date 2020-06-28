package com.themovie.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.adapter_video.view.*

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?){
    cacheImage(view.context, imageUrl.toString(), view)
}

@BindingAdapter("backdrop_image")
fun setBackdropImage(view: ImageView, path: String?){
    val url = "${ApiUrl.IMG_BACK}${path.orEmpty()}"
    cacheImage(view.context, url, view)
}

@BindingAdapter("poster_image")
fun setPosterImage(view: ImageView, path: String?){
    val url = "${ApiUrl.IMG_POSTER}${path.orEmpty()}"
    cacheImage(view.context, url, view)
}

@BindingAdapter("date_release")
fun setDateRelease(view: TextView, date: String?){
    view.text = date?.convertDate()
}

@BindingAdapter("thumbnail")
fun setThumbnail(view: ImageView, key: String?){
    val url = ApiUrl.THUMBNAIL.replace("key", key.orEmpty())
    cacheImage(view.context, url, view)
}