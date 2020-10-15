package com.aldebaran.data.network

import com.aldebaran.domain.Result
import okhttp3.ResponseBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeCallApi(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            Result.success(body)
        }
        else Result.error("Error ${response.code()}: ${response.errorBody()?.getErrorMessage()}")
    } catch (e: IOException) {
        Result.error("Network Failure")
    }  catch (e: Exception) {
        Result.error(e.message.toString())
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