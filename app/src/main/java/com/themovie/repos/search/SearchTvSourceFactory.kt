package com.themovie.repos.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.CoroutineScope

class SearchTvSourceFactory(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val query: String? = ""
): DataSource.Factory<Int, Tv>() {

    private val searchDataSource = MutableLiveData<SearchTvDataSourceBase>()

    override fun create(): DataSource<Int, Tv> {
        val dataSource = SearchTvDataSourceBase(
            scope,
            apiInterface,
            query
        )
        searchDataSource.postValue(dataSource)
        return dataSource
    }

    fun getResultSearch(): MutableLiveData<SearchTvDataSourceBase> = searchDataSource
}