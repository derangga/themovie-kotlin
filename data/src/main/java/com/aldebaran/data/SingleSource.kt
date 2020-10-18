package com.aldebaran.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.aldebaran.domain.Result
import com.aldebaran.domain.Result.Status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

// Single source of truth
fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        val source: LiveData<Result<T>> = databaseQuery.invoke().map { Result.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            responseStatus.data?.let { saveCallResult.invoke(it) }
        } else if (responseStatus.status == ERROR) {
            emit(Result.error(responseStatus.message.orEmpty()))
            emitSource(source)
        }
    }

fun <T, A> resultFlowData(
    databaseQuery: suspend () -> T,
    networkCall: suspend () -> Result<A>,
    saveData: suspend (A) -> T
): Flow<Result<T>> {

    return flow {
        val local = databaseQuery.invoke()
        emit(Result.success(local))

        val network = networkCall.invoke()
        when (network.status) {
            SUCCESS -> {
                network.data?.let {
                    val newData = saveData.invoke(it)
                    emit(Result.success(newData))
                }
            }
            else -> {
                emit(Result.error(network.message.orEmpty()))
                emit(Result.success(local))
            }
        }
    }.flowOn(Dispatchers.IO)
}