package com.themovie.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingImage {
    @BindingAdapter("posterImage")
    @JvmStatic
    fun loadMovieImage(view: ImageView, imageUrl: String?){
        ImageCache.setImageViewUrl(view.context, imageUrl.toString(), view)
    }

    @BindingAdapter("profilePict")
    @JvmStatic
    fun loadProfilePicture(view: ImageView, photoUrl: String?){
        ImageCache.setImageViewUrl(view.context, photoUrl.toString(), view)
    }
}