package com.aldebaran.data

import com.aldebaran.network.Result
import com.aldebaran.network.Result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

// Single source of truth
fun <T: Any, A: Any> resultFlowData(
    localSource: suspend () -> T,
    remoteSource: suspend () -> Result<A>,
    saveData: suspend (A) -> T
): Flow<Result<T>> {
    return flow {
        var local = localSource.invoke()
        emit(Success(local))

        when (val remote = remoteSource.invoke()) {
            is Success -> {
                local = saveData.invoke(remote.data)
                emit(Success(local))
            }
            is Error -> {
                emit(Error(remote.exception))
                emit(Success(local))
            }
        }
    }.flowOn(Dispatchers.IO)
}