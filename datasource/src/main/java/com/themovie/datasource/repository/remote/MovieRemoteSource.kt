package com.themovie.datasource.repository.remote

import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.DetailMovie
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video
import com.themovie.core.network.Result

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