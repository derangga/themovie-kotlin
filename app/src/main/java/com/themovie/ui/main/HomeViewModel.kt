package com.themovie.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.local.*

class HomeViewModel @ViewModelInject constructor (
    private val genreRepos: Repository.GenreRepos,
    private val trendingRepos: Repository.TrendingRepos,
    private val upcomingRepos: Repository.UpcomingRepos,
    private val tvRepos: Repository.TvRepos,
    private val movieRepos: Repository.MovieRepos,
) : ViewModel() {

    val trendingMovies: LiveData<Result<List<TrendingEntity>>>
        get() = trendingRepos.getPopularMovieFromLocalOrRemote()

    val upcomingMovies: LiveData<Result<List<UpcomingEntity>>>
        get() = upcomingRepos.getUpcomingFromLocalOrRemote()

    val genreMovies: LiveData<Result<List<GenreEntity>>>
        get() = genreRepos.getGenreFromLocalOrRemote()

    val discoverTv: LiveData<Result<List<TvEntity>>>
        get() = tvRepos.getTvFromLocalOrRemote()

    val discoverMovies: LiveData<Result<List<MovieEntity>>>
        get() = movieRepos.getDIscoverMovieFromLocalOrRemote()
}