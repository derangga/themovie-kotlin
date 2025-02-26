package com.themovie.core.network

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}

val <T> T.exhaustive: T
    get() = this