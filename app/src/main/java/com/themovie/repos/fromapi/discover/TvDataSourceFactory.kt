package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovertv.Tv
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvDataSourceFactory
    @Inject constructor(private val tvDataSource: TvDataSource) : DataSource.Factory<Int, Tv>() {

    private val tvSourceLiveData = MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, Tv> {
        tvSourceLiveData.postValue(tvDataSource)
        return tvDataSource
    }

    fun getTvDataSource(): MutableLiveData<TvDataSource> {
        return tvSourceLiveData
    }
}