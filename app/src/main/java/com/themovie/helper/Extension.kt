package com.themovie.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.themovie.R
import java.text.ParseException
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.convertDate(): String{
    val simpleDate = SimpleDateFormat("yyyy-M-dd")
    var formattedDate: String? = null
    try{
        val newDate = simpleDate.parse(this)
        formattedDate = SimpleDateFormat("MMM dd, yyyy").format(newDate!!)
    } catch (e: ParseException){
        e.printStackTrace()
    }

    return formattedDate.orEmpty()

}

fun <T:ImageView> cacheImage(context: Context, url: String, imageView: T){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.no_image)
        .error(R.drawable.no_image)
        .into(imageView)
}