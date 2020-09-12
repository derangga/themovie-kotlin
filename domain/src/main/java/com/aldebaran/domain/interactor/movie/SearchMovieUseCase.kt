package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class SearchMovieUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<DataList<MovieResponse>, SearchMovieUseCase.SearchMovieQuery>(){

    override suspend fun run(params: SearchMovieQuery): Result<DataList<MovieResponse>> {
        return movieRemoteSource.searchMovie(params.query, params.page)
    }

    data class SearchMovieQuery(
        val query: String,
        val page: Int
    )
}