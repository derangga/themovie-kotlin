package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.online.discovermv.Movies
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(val composite: CompositeDisposable): DataSource.Factory<Int, Movies>() {

    private val movieDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val movieDataSource = MovieDataSource(composite)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun getMovieDataSource(): MutableLiveData<MovieDataSource> {
        return movieDataSourceLiveData
    }
}