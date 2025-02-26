package com.aldebaran.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldebaran.data.network.source.SearchTvPagingSource
import com.aldebaran.data.network.source.TvPagingSource
import com.aldebaran.data.resultFlowData
import com.aldebaran.domain.entities.mapper.toTv
import com.aldebaran.domain.entities.mapper.toTvEntity
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.TvLocalSource
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.network.Result
import kotlinx.coroutines.flow.Flow

class TvRepository(
    private val localSource: TvLocalSource,
    private val remoteSource: TvRemoteSource
) : Repository.TvRepos {

    override fun getTvFromLocalOrRemote(): Flow<Result<List<Tv>>> {
        return resultFlowData(
            localSource = { localSource.getAll().map { it.toTv() } },
            remoteSource = { remoteSource.getDiscoverTv(1) },
            saveData = { body ->
                if (localSource.isNotEmpty()) {
                    body.forEachIndexed { index, tv -> localSource.update(tv.toTvEntity(index + 1)) }
                } else {
                    val entity = body.map { it.toTvEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getAll().map { it.toTv() }
            }
        )
    }

    override fun getDiscoverTvPaging(): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { TvPagingSource(remoteSource) }
        ).flow
    }

    override fun searchTvShow(query: String): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { SearchTvPagingSource(remoteSource, query) }
        ).flow
    }
}