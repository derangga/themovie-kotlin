package com.themovie.repos.fromapi.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope

class SearchMovieSourceFactory(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val query: String? = ""
): DataSource.Factory<Int, Movies>() {

    private val searchDataSource = MutableLiveData<SearchMovieDataSource>()

    override fun create(): DataSource<Int, Movies> {
        val dataSource = SearchMovieDataSource(scope, apiInterface, query)
        searchDataSource.postValue(dataSource)
        return dataSource
    }

    fun getResultSearch(): MutableLiveData<SearchMovieDataSource> = searchDataSource
}