package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovertv.Tv
import io.reactivex.disposables.CompositeDisposable

class TvDataSourceFactory(private val composite: CompositeDisposable) : DataSource.Factory<Int, Tv>() {

    private val tvSourceLiveData = MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, Tv> {
        val tvDataSource = TvDataSource(composite)
        tvSourceLiveData.postValue(tvDataSource)
        return tvDataSource
    }

    fun getTvDataSource(): MutableLiveData<TvDataSource> {
        return tvSourceLiveData
    }
}