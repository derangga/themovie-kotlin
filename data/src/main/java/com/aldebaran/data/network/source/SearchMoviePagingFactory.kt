package com.aldebaran.data.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import kotlinx.coroutines.CoroutineScope

class SearchMoviePagingFactory(
    private val scope: CoroutineScope,
    private val remote: MovieRemoteSource,
    private val query: String = ""
): DataSource.Factory<Int, MovieResponse>() {

    private val searchDataSource = MutableLiveData<SearchMoviePagingSource>()

    override fun create(): DataSource<Int, MovieResponse> {
        val dataSource = SearchMoviePagingSource(
            scope,
            remote,
            query
        )
        searchDataSource.postValue(dataSource)
        return dataSource
    }

    fun getResultSearch(): MutableLiveData<SearchMoviePagingSource> = searchDataSource
}