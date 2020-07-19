package com.themovie.repos

import com.themovie.BuildConfig
import com.themovie.helper.Constant
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
import com.themovie.repos.RemoteSource
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BaseRemoteDataSource
import com.themovie.restapi.Result

class RemoteSourceImpl(
    private val apiInterface: ApiInterface
): BaseRemoteDataSource(), RemoteSource {
    override suspend fun getPopularMovie(page: Int): Result<PopularResponse> {
        return getResult { apiInterface.getPopularMovie(BuildConfig.TOKEN, Constant.LANGUAGE, page) }
    }

    override suspend fun getGenreMovie(): Result<GenreResponse> {
        return getResult { apiInterface.getGenres(BuildConfig.TOKEN) }
    }

    override suspend fun getUpcomingMovie(page: Int): Result<UpcomingResponse> {
        return getResult { apiInterface.getUpcomingMovies(BuildConfig.TOKEN, page) }
    }

    override suspend fun getDiscoverTv(page: Int): Result<TvResponse> {
        return getResult { apiInterface.getDiscoverTvs(BuildConfig.TOKEN, Constant.LANGUAGE,
            Constant.SORTING, page, "US")
        }
    }

    override suspend fun getDiscoverMovie(page: Int): Result<MoviesResponse> {
        return getResult { apiInterface.getDiscoverMovies(BuildConfig.TOKEN, Constant.LANGUAGE,
            Constant.SORTING, page, "2019", "") }
    }

    override suspend fun getDetailMovie(movieId: Int): Result<DetailMovieResponse> {
        return getResult { apiInterface.getMovieDetail(movieId, BuildConfig.TOKEN) }
    }

    override suspend fun getDetailTv(tvId: Int): Result<DetailTvResponse> {
        return getResult { apiInterface.getTvDetail(tvId, BuildConfig.TOKEN) }
    }

    override suspend fun getCreditMovie(movieId: Int): Result<CastResponse> {
        return getResult { apiInterface.getCreditsMovie(movieId, BuildConfig.TOKEN) }
    }

    override suspend fun getCreditTv(tvId: Int): Result<CastResponse> {
        return getResult { apiInterface.getCreditsTv(tvId, BuildConfig.TOKEN) }
    }

    override suspend fun getRecommendationMovie(movieId: Int): Result<MoviesResponse> {
        return getResult { apiInterface.getRecommendationMovie(movieId, BuildConfig.TOKEN, Constant.LANGUAGE, 1) }
    }

    override suspend fun getRecommendationTv(tvId: Int): Result<TvResponse> {
        return getResult { apiInterface.getRecommendationTv(tvId, BuildConfig.TOKEN, Constant.LANGUAGE, 1) }
    }

    override suspend fun getReviewMovie(movieId: Int): Result<ReviewResponse> {
        return getResult { apiInterface.getReviewsMovie(movieId, BuildConfig.TOKEN, Constant.LANGUAGE, 1) }
    }

    override suspend fun getReviewTv(tvId: Int): Result<ReviewResponse> {
        return getResult { apiInterface.getReviewsTV(tvId, BuildConfig.TOKEN, Constant.LANGUAGE, 1) }
    }

    override suspend fun getTrailerMovie(movieId: Int): Result<VideoResponse> {
        return getResult { apiInterface.getTrailerMovie(movieId, BuildConfig.TOKEN, Constant.LANGUAGE) }
    }

    override suspend fun getTrailerTv(tvId: Int): Result<VideoResponse> {
        return getResult { apiInterface.getTrailerTv(tvId, BuildConfig.TOKEN, Constant.LANGUAGE) }
    }

    override suspend fun getDetailPerson(personId: Int): Result<PersonResponse> {
        return getResult { apiInterface.getPerson(personId, BuildConfig.TOKEN) }
    }

    override suspend fun getPersonFilm(personId: Int): Result<PersonFilmResponse> {
        return getResult { apiInterface.getPersonFilm(personId, BuildConfig.TOKEN) }
    }

    override suspend fun getPersonPict(personId: Int): Result<PersonImageResponse> {
        return getResult { apiInterface.getPersonImages(personId, BuildConfig.TOKEN) }
    }

    override suspend fun getSuggestSearchTv(query: String): Result<TvResponse> {
        return getResult { apiInterface.getSearchTv(BuildConfig.TOKEN, Constant.LANGUAGE, query, 1) }
    }

    override suspend fun getSuggestSearchMovie(query: String): Result<MoviesResponse> {
        return getResult { apiInterface.getSearchMovie(BuildConfig.TOKEN, Constant.LANGUAGE, query, 1) }
    }
}