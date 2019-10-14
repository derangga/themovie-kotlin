package com.themovie.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.discover.MovieDataSourceFactory
import javax.inject.Inject

class DiscoverMovieViewModelFactory
@Inject constructor(private val moviesSourceFactory: MovieDataSourceFactory)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieDataSourceFactory::class.java)
            .newInstance(moviesSourceFactory)
    }
}