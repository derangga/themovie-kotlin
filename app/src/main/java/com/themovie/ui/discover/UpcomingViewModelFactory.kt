package com.themovie.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.discover.UpcomingDataSourceFactory
import javax.inject.Inject

class UpcomingViewModelFactory
@Inject constructor(private val upcomingSourceFactory: UpcomingDataSourceFactory)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UpcomingDataSourceFactory::class.java)
            .newInstance(upcomingSourceFactory)
    }
}