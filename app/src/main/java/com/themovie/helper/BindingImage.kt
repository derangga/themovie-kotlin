package com.themovie.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingImage {
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?){
        ImageCache.setImageViewUrl(view.context, imageUrl.toString(), view)
    }
}