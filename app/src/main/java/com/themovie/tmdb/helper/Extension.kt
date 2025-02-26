package com.themovie.tmdb.helper

import android.annotation.SuppressLint
import android.widget.ImageView
import com.themovie.datasource.entities.ui.Genre
import com.bumptech.glide.Glide
import com.themovie.tmdb.R
import java.text.ParseException
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String?.convertDate(): String {
    val simpleDate = SimpleDateFormat("yyyy-M-dd")
    return try{
        val newDate = simpleDate.parse(this.orEmpty())
        SimpleDateFormat("MMM dd, yyyy").format(newDate!!)
    } catch (e: ParseException){
        ""
    }
}

fun ImageView.cacheImage(url: String){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.no_image)
        .error(R.drawable.no_image)
        .into(this)
}

fun List<Genre>?.concatListGenres(): String {
    return this?.joinToString { it.name }.orEmpty()
}
