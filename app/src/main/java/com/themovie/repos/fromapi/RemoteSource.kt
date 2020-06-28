package com.themovie.repos.fromapi

import com.themovie.model.online.PopularResponse
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonImageResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.video.VideoResponse
import com.themovie.restapi.Result

interface RemoteSource {
    suspend fun getPopularMovie(page: Int): Result<PopularResponse>

    suspend fun getGenreMovie(): Result<GenreResponse>

    suspend fun getUpcomingMovie(page: Int): Result<UpcomingResponse>

    suspend fun getDiscoverTv(page: Int): Result<TvResponse>

    suspend fun getDiscoverMovie(page: Int): Result<MoviesResponse>

    suspend fun getDetailMovie(movieId: Int): Result<DetailMovieResponse>

    suspend fun getDetailTv(tvId: Int): Result<DetailTvResponse>

    suspend fun getCreditMovie(movieId: Int): Result<CastResponse>

    suspend fun getCreditTv(tvId: Int): Result<CastResponse>

    suspend fun getRecommendationMovie(movieId: Int): Result<MoviesResponse>

    suspend fun getRecommendationTv(tvId: Int): Result<TvResponse>

    suspend fun getReviewMovie(movieId: Int): Result<ReviewResponse>

    suspend fun getReviewTv(tvId: Int): Result<ReviewResponse>

    suspend fun getTrailerMovie(movieId: Int): Result<VideoResponse>

    suspend fun getTrailerTv(tvId: Int): Result<VideoResponse>

    suspend fun getDetailPerson(personId: Int): Result<PersonResponse>

    suspend fun getPersonFilm(personId: Int): Result<PersonFilmResponse>

    suspend fun getPersonPict(personId: Int): Result<PersonImageResponse>

    suspend fun getSuggestSearchTv(query: String): Result<TvResponse>

    suspend fun getSuggestSearchMovie(query: String): Result<MoviesResponse>
}