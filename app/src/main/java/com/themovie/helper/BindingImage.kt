package com.themovie.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.themovie.restapi.ApiUrl

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?){
    cacheImage(view.context, imageUrl.toString(), view)
}

@BindingAdapter("network_image")
fun setNetworkImage(view: ImageView, path: String?){
    val url = "${ApiUrl.IMG_BACK}${path.orEmpty()}"
    cacheImage(view.context, url, view)
}