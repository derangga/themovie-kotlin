package com.themovie.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.themovie.model.db.*
import com.themovie.repos.fromapi.TheMovieRepository
import com.themovie.restapi.Result
import javax.inject.Inject

class HomeViewModel @Inject constructor (
    private val repos: TheMovieRepository
) : ViewModel() {

    val trendingMovies: LiveData<Result<List<Trending>>>
        get() = repos.observeTrending()

    val upcomingMovies: LiveData<Result<List<Upcoming>>>
        get() = repos.observeUpcoming()

    val genreMovies: LiveData<Result<List<Genre>>>
        get() = repos.observeGenre()

    val discoverTv: LiveData<Result<List<Tv>>>
        get() = repos.observeDiscoverTv()

    val discoverMovies: LiveData<Result<List<Movies>>>
        get() = repos.observeDiscoverMovie()
}