package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovermv.Movies
import io.reactivex.disposables.CompositeDisposable

class UpcomingDataSourceFactory(private val composite: CompositeDisposable): DataSource.Factory<Int, Movies>() {

    private val upcomingLiveDataSource = MutableLiveData<UpcomingDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val upcomingDataSource = UpcomingDataSource(composite)
        upcomingLiveDataSource.postValue(upcomingDataSource)
        return upcomingDataSource
    }

    fun getUpcomingDataSource(): MutableLiveData<UpcomingDataSource> {
        return upcomingLiveDataSource
    }
}