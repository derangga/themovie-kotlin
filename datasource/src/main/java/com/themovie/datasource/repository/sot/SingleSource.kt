package com.themovie.datasource.repository.sot

import com.themovie.core.network.Result
import com.themovie.core.network.Result.Success
import com.themovie.core.network.Result.Error
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