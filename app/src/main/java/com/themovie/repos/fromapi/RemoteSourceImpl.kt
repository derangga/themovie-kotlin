package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.PopularResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BaseRemoteDataSource
import com.themovie.restapi.Result

class RemoteSourceImpl(
    private val apiInterface: ApiInterface
): BaseRemoteDataSource(), RemoteSource {
    override suspend fun getPopularMovie(page: Int): Result<PopularResponse> {
        return getResult { apiInterface.getPopularMovie(ApiUrl.TOKEN, Constant.LANGUAGE, page) }
    }

    override suspend fun getGenreMovie(): Result<GenreResponse> {
        return getResult { apiInterface.getGenres(ApiUrl.TOKEN) }
    }

    override suspend fun getUpcomingMovie(page: Int): Result<UpcomingResponse> {
        return getResult { apiInterface.getUpcomingMovies(ApiUrl.TOKEN, page) }
    }

    override suspend fun getDiscoverTv(page: Int): Result<TvResponse> {
        return getResult { apiInterface.getDiscoverTvs(ApiUrl.TOKEN, Constant.LANGUAGE,
            Constant.SORTING, page, "US")
        }
    }

    override suspend fun getDiscoverMovie(page: Int): Result<MoviesResponse> {
        return getResult { apiInterface.getDiscoverMovies(ApiUrl.TOKEN, Constant.LANGUAGE,
            Constant.SORTING, page, "2019", "") }
    }
}