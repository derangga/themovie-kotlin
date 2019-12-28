package com.themovie.restapi

import com.themovie.helper.getErrorMessage
import retrofit2.Response
import timber.log.Timber

abstract class BaseRemoteDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error("Error ${response.code()}: ${response.errorBody()?.getErrorMessage()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        return Result.error(message)
    }
}