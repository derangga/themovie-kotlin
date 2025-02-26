package com.themovie.datasource.repository.sot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themovie.core.network.Result
import com.themovie.datasource.repository.remote.source.SearchTvPagingSource
import com.themovie.datasource.repository.remote.source.TvPagingSource
import com.themovie.datasource.entities.mapper.toTv
import com.themovie.datasource.entities.mapper.toTvEntity
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.Repository
import com.themovie.datasource.repository.local.TvLocalSource
import com.themovie.datasource.repository.remote.TvRemoteSource
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