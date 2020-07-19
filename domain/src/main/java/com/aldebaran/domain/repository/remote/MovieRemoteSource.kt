package com.aldebaran.domain.repository.remote

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*

interface MovieRemoteSource {

    suspend fun getPopularMovie(page: Int): Result<DataList<MovieResponse>>

    suspend fun getGenreMovie(): Result<GenreResponse>

    suspend fun getUpcomingMovie(): Result<DataList<MovieResponse>>

    suspend fun getDiscoverMovie(withGenres: String, page: Int): Result<DataList<MovieResponse>>

    suspend fun getDetailMovie(movieId: Int): Result<DetailMovieResponse>

    suspend fun getRecommendationMovie(movieId: Int): Result<DataList<MovieResponse>>

    suspend fun getReviewMovie(movieId: Int): Result<DataList<ReviewsResponse>>

    suspend fun getCreditMovie(movieId: Int): Result<CastResponse>

    suspend fun getTrailerMovie(movieId: Int): Result<VideoResponse>

    suspend fun searchMovie(query: String, page: Int): Result<DataList<MovieResponse>>
}