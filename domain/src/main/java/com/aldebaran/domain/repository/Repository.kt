package com.aldebaran.domain.repository

import androidx.lifecycle.LiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.local.*

interface Repository {

    interface GenreRepos {
        fun getGenreFromLocalOrRemote() : LiveData<Result<List<GenreEntity>>>
    }

    interface MovieRepos {
        fun getDIscoverMovieFromLocalOrRemote(): LiveData<Result<List<MovieEntity>>>
    }

    interface TrendingRepos {
        fun getPopularMovieFromLocalOrRemote() : LiveData<Result<List<TrendingEntity>>>
    }

    interface TvRepos {
        fun getTvFromLocalOrRemote(): LiveData<Result<List<TvEntity>>>
    }

    interface UpcomingRepos {
        fun getUpcomingFromLocalOrRemote(): LiveData<Result<List<UpcomingEntity>>>
    }
}