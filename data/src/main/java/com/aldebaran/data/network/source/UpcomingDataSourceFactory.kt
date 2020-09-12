package com.aldebaran.data.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import kotlinx.coroutines.CoroutineScope

class UpcomingDataSourceFactory(
    private val scope: CoroutineScope,
    private val remote: MovieRemoteSource
): DataSource.Factory<Int, MovieResponse>() {

    private val upcomingLiveDataSource = MutableLiveData<UpcomingDataSource>()

    override fun create(): DataSource<Int, MovieResponse> {
        val dataSource = UpcomingDataSource(scope, remote)
        upcomingLiveDataSource.postValue(dataSource)
        return dataSource
    }

    fun getUpcomingDataSource(): MutableLiveData<UpcomingDataSource> {
        return upcomingLiveDataSource
    }
}