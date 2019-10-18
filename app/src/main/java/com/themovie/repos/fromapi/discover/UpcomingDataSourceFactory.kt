package com.themovie.repos.fromapi.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
class UpcomingDataSourceFactory
   (private val scope: CoroutineScope,
    private val apiInterface: ApiInterface): DataSource.Factory<Int, Movies>() {

    private val upcomingLiveDataSource = MutableLiveData<UpcomingDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val dataSource = UpcomingDataSource(scope, apiInterface)
        upcomingLiveDataSource.postValue(dataSource)
        return dataSource
    }

    fun getUpcomingDataSource(): MutableLiveData<UpcomingDataSource> {
        return upcomingLiveDataSource
    }
}