package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovertv.Tv
import com.themovie.restapi.ApiInterface
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvDataSourceFactory
    @Inject constructor(private val apiInterface: ApiInterface) : DataSource.Factory<Int, Tv>() {

    private val tvSourceLiveData = MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, Tv> {
        val tvDataSource = TvDataSource(apiInterface)
        tvSourceLiveData.postValue(tvDataSource)
        return tvDataSource
    }

    fun getTvDataSource(): MutableLiveData<TvDataSource> {
        return tvSourceLiveData
    }
}