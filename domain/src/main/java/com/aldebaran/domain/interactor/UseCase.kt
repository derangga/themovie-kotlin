package com.aldebaran.domain.interactor
import com.aldebaran.domain.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<T, in Params> {

    abstract suspend fun run(params: Params): Result<T>

    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Result<T>) -> Unit){
        scope.launch {
            val job = async(IO) { run(params) }
            onResult(job.await())
        }
    }

    class None
}