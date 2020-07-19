package com.themovie.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import com.themovie.R
import com.themovie.model.db.Genre
import com.themovie.restapi.Result
import com.themovie.restapi.Result.Status.ERROR
import com.themovie.restapi.Result.Status.SUCCESS
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
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

fun List<Genre>.concatListGenres(): String {
    val genre = StringBuilder()
    for(i in this.indices){
        if(this.size > 1){
            if(i != this.size - 1) {
                genre.append(this[i].name).append(", ")
            } else genre.append(this[i].name)
        } else genre.append(this[i].name)
    }
    return genre.toString()
}

fun ResponseBody.getErrorMessage(): String {
    return try {
        val jsonParser = JSONObject(this.string())
        jsonParser.getString("errors")
    }catch (e: JSONException){
        e.message.orEmpty()
    }
}

// Single source of truth
fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                          networkCall: suspend () -> Result<A>,
                          saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        val source = databaseQuery.invoke().map { Result.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }
        } else if (responseStatus.status == ERROR) {
            emit(Result.error(responseStatus.message.orEmpty()))
            emitSource(source)
        }
}