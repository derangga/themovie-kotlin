package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.GenreResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetGenreUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<GenreResponse, UseCase.None>(){
    override suspend fun run(params: None): Result<GenreResponse> {
        return movieRemoteSource.getGenreMovie()
    }
}