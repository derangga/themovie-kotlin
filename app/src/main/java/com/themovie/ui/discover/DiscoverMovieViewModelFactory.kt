package com.themovie.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.discover.MovieDataSourceFactory
import com.themovie.restapi.ApiInterface
import javax.inject.Inject

class DiscoverMovieViewModelFactory
@Inject constructor(private val apiInterface: ApiInterface)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApiInterface::class.java)
            .newInstance(apiInterface)
    }
}