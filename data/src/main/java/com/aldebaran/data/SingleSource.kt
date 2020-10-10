package com.aldebaran.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.aldebaran.domain.Result
import com.aldebaran.domain.Result.Status.ERROR
import com.aldebaran.domain.Result.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

// Single source of truth
fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                          networkCall: suspend () -> Result<A>,
                          saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        val source: LiveData<Result<T>> = databaseQuery.invoke().map { Result.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }
        } else if (responseStatus.status == ERROR) {
            emit(Result.error(responseStatus.message.orEmpty()))
            emitSource(source)
        }
    }