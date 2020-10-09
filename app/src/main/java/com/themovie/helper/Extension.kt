package com.themovie.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.aldebaran.domain.entities.remote.GenreRemote
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

fun List<GenreRemote>?.concatListGenres(): String {
    val genre = StringBuilder()
    return this?.let {
        for(i in this.indices){
            if(this.size > 1){
                if(i != this.size - 1) {
                    genre.append(this[i].name).append(", ")
                } else genre.append(this[i].name)
            } else genre.append(this[i].name)
        }
        genre.toString()
    }.orEmpty()
}

fun ResponseBody.getErrorMessage(): String {
    return try {
        val jsonParser = JSONObject(this.string())
        jsonParser.getString("errors")
    }catch (e: JSONException){
        e.message.orEmpty()
    }
}