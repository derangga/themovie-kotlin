package com.themovie.restapi

import okhttp3.ResponseBody

interface ApiCallback<T> {
    fun onSuccessRequest(response: T?)
    fun onErrorRequest(errorBody: ResponseBody?)
    fun onFailure(e: Exception)
}