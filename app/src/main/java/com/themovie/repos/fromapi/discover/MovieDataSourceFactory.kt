package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiInterface
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataSourceFactory
    @Inject constructor(private val apiInterface: ApiInterface): DataSource.Factory<Int, Movies>() {

    private val movieDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val dataSource = MovieDataSource(apiInterface)
        movieDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getMovieDataSource(): MutableLiveData<MovieDataSource> {
        return movieDataSourceLiveData
    }
}