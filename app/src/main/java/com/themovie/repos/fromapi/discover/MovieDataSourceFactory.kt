package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface
): DataSource.Factory<Int, Movies>() {

    private val movieDataSourceLiveData = MutableLiveData<MovieDataSourceBase>()
    var genre: String = ""

    override fun create(): DataSource<Int, Movies> {
        val dataSource = MovieDataSourceBase(scope, apiInterface, genre)
        movieDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getMovieDataSource(): MutableLiveData<MovieDataSourceBase> {
        return movieDataSourceLiveData
    }
}