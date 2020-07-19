package com.aldebaran.data.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource
import kotlinx.coroutines.CoroutineScope

class SearchTvPagingFactory(
    private val scope: CoroutineScope,
    private val remote: TvRemoteSource,
    private val query: String = ""
): DataSource.Factory<Int, TvResponse>() {

    private val searchDataSource = MutableLiveData<SearchTvPagingSource>()

    override fun create(): DataSource<Int, TvResponse> {
        val dataSource = SearchTvPagingSource(
            scope,
            remote,
            query
        )
        searchDataSource.postValue(dataSource)
        return dataSource
    }

    fun getResultSearch(): MutableLiveData<SearchTvPagingSource> = searchDataSource
}