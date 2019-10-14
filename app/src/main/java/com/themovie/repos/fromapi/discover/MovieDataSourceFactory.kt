package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovermv.Movies
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataSourceFactory
    @Inject constructor(private val movieDataSource: MovieDataSource): DataSource.Factory<Int, Movies>() {

    private val movieDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movies> {
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun getMovieDataSource(): MutableLiveData<MovieDataSource> {
        return movieDataSourceLiveData
    }
}