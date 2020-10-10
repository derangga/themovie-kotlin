package com.aldebaran.data.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource
import kotlinx.coroutines.CoroutineScope

class TvPagingFactory(
    private val scope: CoroutineScope,
    private val remote: TvRemoteSource
) : DataSource.Factory<Int, TvResponse>() {

    private val tvSourceLiveData = MutableLiveData<TvPagingSource>()

    override fun create(): DataSource<Int, TvResponse> {
        val tvDataSource =
            TvPagingSource(scope, remote)
        tvSourceLiveData.postValue(tvDataSource)
        return tvDataSource
    }

    fun getTvDataSource(): MutableLiveData<TvPagingSource> {
        return tvSourceLiveData
    }
}