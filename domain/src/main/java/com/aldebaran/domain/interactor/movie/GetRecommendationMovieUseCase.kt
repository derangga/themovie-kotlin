package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetRecommendationMovieUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<DataList<MovieResponse>, Int>(){

    override suspend fun run(params: Int): Result<DataList<MovieResponse>> {
        return movieRemoteSource.getRecommendationMovie(params)
    }
}