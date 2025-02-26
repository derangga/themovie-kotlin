package com.aldebaran.domain.repository.remote

import com.aldebaran.domain.entities.ui.*
import com.aldebaran.network.Result

interface MovieRemoteSource {

    suspend fun getPopularMovie(page: Int): Result<List<Movie>>

    suspend fun getGenreMovie(): Result<List<Genre>>

    suspend fun getUpcomingMovie(page: Int): Result<List<Movie>>

    suspend fun getDiscoverMovie(withGenres: String, primaryReleaseYear: Int, page: Int): Result<List<Movie>>

    suspend fun getDetailMovie(movieId: Int): Result<DetailMovie>

    suspend fun getRecommendationMovie(movieId: Int): Result<List<Movie>>

    suspend fun getReviewMovie(movieId: Int): Result<List<Review>>

    suspend fun getCreditMovie(movieId: Int): Result<List<Credit>>

    suspend fun getTrailerMovie(movieId: Int): Result<List<Video>>

    suspend fun searchMovie(query: String, page: Int): Result<List<Movie>>
}