package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldebaran.data.network.source.SearchTvPagingSource
import com.aldebaran.data.network.source.TvPagingSource
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.local.TvEntity
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.entities.toTvEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.TvLocalSource
import com.aldebaran.domain.repository.remote.TvRemoteSource
import kotlinx.coroutines.flow.Flow

class TvRepository (
    private val local: TvLocalSource,
    private val remote: TvRemoteSource
): Repository.TvRepos {

    override fun getTvFromLocalOrRemote(): LiveData<Result<List<TvEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getDiscoverTv() },
            networkCall = { remote.getDiscoverTv(1) },
            saveCallResult = { res ->
                val rows = local.tvRows()
                if(rows == 0) {
                    res.results.forEach { local.insertDiscoverTv(it.toTvEntity()) }
                } else {
                    res.results.forEachIndexed { key, tv ->
                        local.updateDiscoverTv(tv.toTvEntity(key + 1))
                    }
                }
            }
        )
    }

    override fun getDiscoverTvPaging(): Flow<PagingData<TvResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { TvPagingSource(remote) }
        ).flow
    }

    override fun searchTvShow(query: String): Flow<PagingData<TvResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { SearchTvPagingSource(remote, query) }
        ).flow
    }
}