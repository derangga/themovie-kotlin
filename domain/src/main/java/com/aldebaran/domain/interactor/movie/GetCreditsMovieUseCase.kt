package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.CastResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetCreditsMovieUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<CastResponse, Int>(){
    override suspend fun run(params: Int): Result<CastResponse> {
        return movieRemoteSource.getCreditMovie(params)
    }
}