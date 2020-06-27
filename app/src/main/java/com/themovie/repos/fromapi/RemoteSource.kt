package com.themovie.repos.fromapi

import com.themovie.model.online.PopularResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.restapi.Result

interface RemoteSource {
    suspend fun getPopularMovie(page: Int): Result<PopularResponse>

    suspend fun getGenreMovie(): Result<GenreResponse>

    suspend fun getUpcomingMovie(page: Int): Result<UpcomingResponse>

    suspend fun getDiscoverTv(page: Int): Result<TvResponse>

    suspend fun getDiscoverMovie(page: Int): Result<MoviesResponse>

}