package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
class MovieDataSourceFactory(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val genre: String = ""
): DataSource.Factory<Int, Movies>() {

    private val movieDataSourceLiveData = MutableLiveData<MovieDataSourceBase>()

    override fun create(): DataSource<Int, Movies> {
        val dataSource = MovieDataSourceBase(scope, apiInterface, genre)
        movieDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getMovieDataSource(): MutableLiveData<MovieDataSourceBase> {
        return movieDataSourceLiveData
    }
}