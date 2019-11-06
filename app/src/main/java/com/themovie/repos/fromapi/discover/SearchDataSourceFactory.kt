package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Movies
import io.reactivex.disposables.CompositeDisposable

class SearchDataSourceFactory(private val composite: CompositeDisposable, private val movieName: String) : DataSource.Factory<Int, Movies>() {

    private val searchMovieLiveData = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val searchDataSource = SearchDataSource(composite, movieName)
        searchMovieLiveData.postValue(searchDataSource)
        return searchDataSource
    }

    fun getSearchMovieSource(): MutableLiveData<SearchDataSource> {
        return searchMovieLiveData
    }
}