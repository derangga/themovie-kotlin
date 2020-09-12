package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetDiscoverMoviesUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<DataList<MovieResponse>, GetDiscoverMoviesUseCase.DiscoverMovieQuery>() {

    override suspend fun run(params: DiscoverMovieQuery): Result<DataList<MovieResponse>> {
        return movieRemoteSource
            .getDiscoverMovie(params.withGenres, params.primaryReleaseYear, params.page)
    }

    data class DiscoverMovieQuery(
        val page: Int,
        val primaryReleaseYear: Int,
        val withGenres: String
    )
}