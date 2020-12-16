package com.themovie.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.aldebaran.domain.entities.remote.GenreResponse
import com.aldebaran.domain.entities.ui.Genre
import com.bumptech.glide.Glide
import com.themovie.R
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
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

fun <T:ImageView> cacheImage(context: Context, url: String, imageView: T){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.no_image)
        .error(R.drawable.no_image)
        .into(imageView)
}

fun List<Genre>?.concatListGenres(): String {
    return this?.joinToString { it.name }.orEmpty()
}

fun ResponseBody.getErrorMessage(): String {
    return try {
        val jsonParser = JSONObject(this.string())
        jsonParser.getString("errors")
    }catch (e: JSONException){
        e.message.orEmpty()
    }
}