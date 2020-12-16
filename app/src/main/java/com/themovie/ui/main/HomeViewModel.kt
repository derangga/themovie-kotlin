package com.themovie.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.network.Result
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.entities.ui.*
import kotlinx.coroutines.flow.Flow

class HomeViewModel @ViewModelInject constructor (
    private val genreRepos: Repository.GenreRepos,
    private val trendingRepos: Repository.TrendingRepos,
    private val upcomingRepos: Repository.UpcomingRepos,
    private val tvRepos: Repository.TvRepos,
    private val movieRepos: Repository.MovieRepos,
) : ViewModel() {

    private val _loading by lazy { MutableLiveData(true) }
    val loading: LiveData<Boolean> get() = _loading

    val trendingMovies: Flow<Result<List<Movie>>>
        get() = trendingRepos.getPopularMovieFromLocalOrRemote()

    val upcomingMovies: Flow<Result<List<Movie>>>
        get() = upcomingRepos.getUpcomingFromLocalOrRemote()

    val genreMovies: Flow<Result<List<Genre>>>
        get() = genreRepos.getGenreFromLocalOrRemote()

    val discoverTv: Flow<Result<List<Tv>>>
        get() = tvRepos.getTvFromLocalOrRemote()

    val discoverMovies: Flow<Result<List<Movie>>>
        get() = movieRepos.getDiscoverMovieFromLocalOrRemote()

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }
}