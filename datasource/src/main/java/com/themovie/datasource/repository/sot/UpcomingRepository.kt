package com.themovie.datasource.repository.sot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themovie.core.network.Result
import com.themovie.datasource.entities.mapper.toMovie
import com.themovie.datasource.entities.mapper.toUpcomingEntity
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.Repository
import com.themovie.datasource.repository.local.UpcomingLocalSource
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.datasource.repository.remote.source.UpcomingPagingSource
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
                    body.forEachIndexed { index, movie -> localSource.update(movie.toUpcomingEntity(index + 1)) }
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