package com.themovie.datasource.repository.remote.source

import com.themovie.datasource.BuildConfig
import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.repository.remote.service.MovieServices
import com.themovie.datasource.entities.mapper.toCast
import com.themovie.datasource.entities.mapper.toGenre
import com.themovie.datasource.entities.mapper.toDetailMovie
import com.themovie.datasource.entities.mapper.toMovie
import com.themovie.datasource.entities.mapper.toReview
import com.themovie.datasource.entities.mapper.toVideo
import com.themovie.datasource.entities.mapper.orEmpty
import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.DetailMovie
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.core.network.Result
import com.themovie.core.network.safeCallApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class MovieRemoteSourceImpl(private val services: MovieServices):
    MovieRemoteSource {
    override suspend fun getPopularMovie(page: Int): Result<List<Movie>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getPopularMovie(BuildConfig.TOKEN, page) },
                onSuccess = { body -> body?.results?.map { it.toMovie() }.orEmpty() }
            )
        }
    }

    override suspend fun getGenreMovie(): Result<List<Genre>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getGenres(BuildConfig.TOKEN) },
                onSuccess = { body -> body?.genres?.map { it.toGenre() }.orEmpty() }
            )
        }
    }

    override suspend fun getUpcomingMovie(page: Int): Result<List<Movie>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getUpcomingMovies(BuildConfig.TOKEN, page) },
                onSuccess = { body -> body?.results?.map { it.toMovie() }.orEmpty() }
            )
        }
    }

    override suspend fun getDiscoverMovie(
        withGenres: String,
        primaryReleaseYear: Int,
        page: Int
    ): Result<List<Movie>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getDiscoverMovies(BuildConfig.TOKEN, ApiUrl.SORTING, page, primaryReleaseYear, withGenres) },
                onSuccess = { body -> body?.results?.map { it.toMovie() }.orEmpty() }
            )
        }
    }

    override suspend fun getDetailMovie(movieId: Int): Result<DetailMovie> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getMovieDetail(movieId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.toDetailMovie().orEmpty() }
            )
        }
    }

    override suspend fun getRecommendationMovie(movieId: Int): Result<List<Movie>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getRecommendationMovie(movieId, BuildConfig.TOKEN, 1) },
                onSuccess = { body -> body?.results?.map { it.toMovie() }.orEmpty() }
            )
        }
    }

    override suspend fun getReviewMovie(movieId: Int): Result<List<Review>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getReviewsMovie(movieId, BuildConfig.TOKEN, 1) },
                onSuccess = { body -> body?.results?.map { it.toReview() }.orEmpty() }
            )
        }
    }

    override suspend fun getCreditMovie(movieId: Int): Result<List<Credit>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getCreditsMovie(movieId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.credits?.map { it.toCast() }.orEmpty() }
            )
        }
    }

    override suspend fun getTrailerMovie(movieId: Int): Result<List<Video>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getTrailerMovie(movieId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.videos?.map { it.toVideo() }.orEmpty() }
            )
        }
    }

    override suspend fun searchMovie(
        query: String,
        page: Int
    ): Result<List<Movie>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getSearchMovie(BuildConfig.TOKEN, query, page) },
                onSuccess = { body -> body?.results?.map { it.toMovie() }.orEmpty() }
            )
        }
    }
}