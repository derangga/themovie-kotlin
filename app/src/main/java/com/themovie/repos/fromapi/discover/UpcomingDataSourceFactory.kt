package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
class UpcomingDataSourceFactory
   (private val scope: CoroutineScope,
    private val apiInterface: ApiInterface): DataSource.Factory<Int, Upcoming>() {

    private val upcomingLiveDataSource = MutableLiveData<UpcomingDataSourceBase>()

    override fun create(): DataSource<Int, Upcoming> {
        val dataSource = UpcomingDataSourceBase(scope, apiInterface)
        upcomingLiveDataSource.postValue(dataSource)
        return dataSource
    }

    fun getUpcomingDataSource(): MutableLiveData<UpcomingDataSourceBase> {
        return upcomingLiveDataSource
    }
}