package com.themovie.tmdb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themovie.core.network.Result
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
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