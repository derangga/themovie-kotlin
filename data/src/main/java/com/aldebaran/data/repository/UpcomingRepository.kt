package com.aldebaran.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldebaran.data.network.source.UpcomingPagingSource
import com.aldebaran.data.resultFlowData
import com.aldebaran.domain.entities.mapper.toMovie
import com.aldebaran.domain.entities.mapper.toTrendingEntity
import com.aldebaran.domain.entities.mapper.toUpcomingEntity
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.UpcomingLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.network.Result
import kotlinx.coroutines.flow.Flow

class UpcomingRepository(
    private val localSource: UpcomingLocalSource,
    private val remoteSource: MovieRemoteSource
) : Repository.UpcomingRepos {

    override fun getUpcomingFromLocalOrRemote(): Flow<Result<List<Movie>>> {
        return resultFlowData(
            localSource = { localSource.getAll().map { it.toMovie() } },
            remoteSource = { remoteSource.getUpcomingMovie(1) },
            saveData = { body ->
                if (localSource.isNotEmpty()) {
                    body.forEachIndexed { index, movie -> localSource.update(movie.toUpcomingEntity(index)) }
                } else {
                    val entity = body.map { it.toUpcomingEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getAll().map { it.toMovie() }
            }
        )
    }

    override fun getUpcomingMoviePaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { UpcomingPagingSource(remoteSource) }
        ).flow
    }
}