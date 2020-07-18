package com.aldebaran.domain.interactor

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.GenreResponse
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.interactor.GetPopularMovie.Params
import com.aldebaran.domain.interactor.UseCase.None
import com.aldebaran.domain.repository.MovieRepository

class GetPopularMovie (
    private val repos: MovieRepository
): UseCase<DataList<MovieResponse>, Params>(){
    override suspend fun run(params: Params): Result<DataList<MovieResponse>> {
        return repos.getPopularMovie(params.page)
    }
    data class Params(val page: Int)
}

class GetUpcomingMovie (
    private val repos: MovieRepository
): UseCase<DataList<MovieResponse>, None>(){
    override suspend fun run(params: None): Result<DataList<MovieResponse>> {
        return repos.getUpcomingMovie()
    }
}

class GetGenres(
    private val repos: MovieRepository
): UseCase<GenreResponse, None>(){
    override suspend fun run(params: None): Result<GenreResponse> {
        return repos.getGenreMovie()
    }
}