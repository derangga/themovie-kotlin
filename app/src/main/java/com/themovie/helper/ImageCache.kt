package com.themovie.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.themovie.R

class ImageCache {
    companion object{
        fun setImageViewUrl(context: Context, url: String, imageViewRes: ImageView){
            Glide.with(context)
                .load(url)
                .error(R.drawable.no_image)
                .into(imageViewRes)
        }

        fun setRoundedImageUrl(context: Context, url: String, roundedImageView: RoundedImageView){
            Glide.with(context)
                .load(url)
                .error(R.drawable.no_image)
                .into(roundedImageView)
        }
    }
}