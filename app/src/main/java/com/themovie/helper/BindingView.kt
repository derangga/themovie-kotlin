package com.themovie.helper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aldebaran.data.network.ApiUrl

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?){
    view.cacheImage("$imageUrl")
}

@BindingAdapter("backdrop_image")
fun setBackdropImage(view: ImageView, path: String?){
    view.cacheImage("${ApiUrl.IMG_BACK}$path")
}

@BindingAdapter("poster_image")
fun setPosterImage(view: ImageView, path: String?){
    view.cacheImage("${ApiUrl.IMG_POSTER}$path")
}

@BindingAdapter("date_release")
fun setDateRelease(view: TextView, date: String?){
    view.text = date?.convertDate()
}

@BindingAdapter("thumbnail")
fun setThumbnail(view: ImageView, key: String?){
    val url = ApiUrl.THUMBNAIL.replace("key", key.orEmpty())
    view.cacheImage(url)
}

@BindingAdapter(value = ["birthDate", "birthPlace"], requireAll = true)
fun setBirthOfDate(view: TextView, birthDate: String?, birthPlace: String?) {
    view.text = if (!birthDate.isNullOrEmpty() && !birthPlace.isNullOrEmpty()) {
        "Born: ${birthDate.convertDate()} ${birthPlace.orEmpty()}"
    } else ""
}