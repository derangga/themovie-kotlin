package com.themovie.repos.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
class TvDataSourceFactory(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface
) : DataSource.Factory<Int, Tv>() {

    private val tvSourceLiveData = MutableLiveData<TvDataSourceBase>()

    override fun create(): DataSource<Int, Tv> {
        val tvDataSource =
            TvDataSourceBase(scope, apiInterface)
        tvSourceLiveData.postValue(tvDataSource)
        return tvDataSource
    }

    fun getTvDataSource(): MutableLiveData<TvDataSourceBase> {
        return tvSourceLiveData
    }
}