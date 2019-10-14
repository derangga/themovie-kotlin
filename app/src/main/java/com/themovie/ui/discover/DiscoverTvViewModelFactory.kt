package com.themovie.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.discover.TvDataSourceFactory
import javax.inject.Inject

class DiscoverTvViewModelFactory
@Inject constructor(private val tvDataSourceFactory: TvDataSourceFactory)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TvDataSourceFactory::class.java)
            .newInstance(tvDataSourceFactory)
    }
}