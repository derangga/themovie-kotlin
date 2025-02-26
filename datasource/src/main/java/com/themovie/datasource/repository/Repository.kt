package com.themovie.datasource.repository

import androidx.paging.PagingData
import com.themovie.core.network.Result
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Tv
import kotlinx.coroutines.flow.Flow

interface Repository {

    interface GenreRepos {
        fun getGenreFromLocalOrRemote() : Flow<Result<List<Genre>>>
    }

    interface MovieRepos {
        fun getDiscoverMovieFromLocalOrRemote(): Flow<Result<List<Movie>>>
        fun getDiscoverMoviePaging(genre: String): Flow<PagingData<Movie>>
        fun searchMoovie(query: String): Flow<PagingData<Movie>>
    }

    interface TrendingRepos {
        fun getPopularMovieFromLocalOrRemote() : Flow<Result<List<Movie>>>
    }

    interface TvRepos {
        fun getTvFromLocalOrRemote(): Flow<Result<List<Tv>>>
        fun getDiscoverTvPaging(): Flow<PagingData<Tv>>
        fun searchTvShow(query: String): Flow<PagingData<Tv>>
    }

    interface UpcomingRepos {
        fun getUpcomingFromLocalOrRemote(): Flow<Result<List<Movie>>>
        fun getUpcomingMoviePaging(): Flow<PagingData<Movie>>
    }
}