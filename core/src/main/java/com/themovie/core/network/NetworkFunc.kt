package com.themovie.core.network

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

suspend fun <T: Any, A: Any> safeCallApi(call: suspend () -> Response<T>, onSuccess: (T?) -> A) : Result<A> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val body = onSuccess.invoke(response.body())
            Result.Success(body)
        } else {
            Result.Error(Exception(response.errorBody()?.getErrorMessage()))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}

fun ResponseBody.getErrorMessage(): String {
    return try {
        val jsonParser = JSONObject(this.string())
        jsonParser.getString("errors")
    }catch (e: JSONException){
        e.message.orEmpty()
    }
}