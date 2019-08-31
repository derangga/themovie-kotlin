package com.themovie.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class DateConverter {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convert(dateOrigin: String): String{
            var simpleDate = SimpleDateFormat("yyyy-M-dd")
            val newDate = simpleDate.parse(dateOrigin)
            simpleDate = SimpleDateFormat("MMM dd, yyyy")
            return simpleDate.format(newDate!!)

        }
    }
}