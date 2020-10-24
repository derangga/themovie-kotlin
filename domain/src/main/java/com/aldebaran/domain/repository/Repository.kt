package com.aldebaran.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.local.*
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.entities.remote.TvResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    interface GenreRepos {
        fun getGenreFromLocalOrRemote() : LiveData<Result<List<GenreEntity>>>
    }

    interface MovieRepos {
        fun getDiscoverMovieFromLocalOrRemote(): LiveData<Result<List<MovieEntity>>>
        fun getDiscoverMoviePaging(genre: String): Flow<PagingData<MovieResponse>>
        fun searchMoovie(query: String): Flow<PagingData<MovieResponse>>
    }

    interface TrendingRepos {
        fun getPopularMovieFromLocalOrRemote() : LiveData<Result<List<TrendingEntity>>>
    }

    interface TvRepos {
        fun getTvFromLocalOrRemote(): LiveData<Result<List<TvEntity>>>
        fun getDiscoverTvPaging(): Flow<PagingData<TvResponse>>
        fun searchTvShow(query: String): Flow<PagingData<TvResponse>>
    }

    interface UpcomingRepos {
        fun getUpcomingFromLocalOrRemote(): LiveData<Result<List<UpcomingEntity>>>
        fun getUpcomingMoviePaging(): Flow<PagingData<MovieResponse>>
    }
}