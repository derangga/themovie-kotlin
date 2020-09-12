package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.DetailMovieResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetMovieDetailUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<DetailMovieResponse, Int>(){
    override suspend fun run(params: Int): Result<DetailMovieResponse> {
        return movieRemoteSource.getDetailMovie(params)
    }
}