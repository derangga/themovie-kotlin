package com.aldebaran.domain.interactor

import androidx.lifecycle.LiveData

abstract class LiveDataUseCase<T, in Params> {
    abstract suspend fun run(params: Params): LiveData<T>
}