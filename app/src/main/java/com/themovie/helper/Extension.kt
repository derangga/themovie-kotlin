package com.themovie.helper

import android.annotation.SuppressLint
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