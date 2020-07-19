package com.aldebaran.data.network.source

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.data.network.safeCallApi
import com.aldebaran.data.network.service.MovieServices
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class MovieRemoteSourceImpl(private val services: MovieServices):
    MovieRemoteSource {
    override suspend fun getPopularMovie(page: Int): Result<DataList<MovieResponse>> {
        return safeCallApi { services.getPopularMovie(BuildConfig.TOKEN, page) }
    }

    override suspend fun getGenreMovie(): Result<GenreResponse> {
        return safeCallApi { services.getGenres(BuildConfig.TOKEN) }
    }

    override suspend fun getUpcomingMovie(): Result<DataList<MovieResponse>> {
        return safeCallApi { services.getUpcomingMovies(BuildConfig.TOKEN) }
    }

    override suspend fun getDiscoverMovie(
        withGenres: String, page: Int
    ): Result<DataList<MovieResponse>> {
        return safeCallApi {
            services.getDiscoverMovies(BuildConfig.TOKEN, ApiUrl.SORTING,
                page, "2020", withGenres) }
    }

    override suspend fun getDetailMovie(movieId: Int): Result<DetailMovieResponse> {
        return safeCallApi { services.getMovieDetail(movieId, BuildConfig.TOKEN) }
    }

    override suspend fun getRecommendationMovie(movieId: Int): Result<DataList<MovieResponse>> {
        return safeCallApi { services.getRecommendationMovie(movieId, BuildConfig.TOKEN, 1) }
    }

    override suspend fun getReviewMovie(movieId: Int): Result<DataList<ReviewsResponse>> {
        return safeCallApi { services.getReviewsMovie(movieId, BuildConfig.TOKEN, 1) }
    }

    override suspend fun getCreditMovie(movieId: Int): Result<CastResponse> {
        return safeCallApi { services.getCreditsMovie(movieId, BuildConfig.TOKEN) }
    }

    override suspend fun getTrailerMovie(movieId: Int): Result<VideoResponse> {
        return safeCallApi { services.getTrailerMovie(movieId, BuildConfig.TOKEN) }
    }

    override suspend fun searchMovie(query: String, page: Int): Result<DataList<MovieResponse>> {
        return safeCallApi { services.getSearchMovie(BuildConfig.TOKEN, query, page) }
    }
}