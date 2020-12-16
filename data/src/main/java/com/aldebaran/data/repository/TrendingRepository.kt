package com.aldebaran.data.repository

import com.aldebaran.data.resultFlowData
import com.aldebaran.domain.entities.mapper.toMovie
import com.aldebaran.domain.entities.mapper.toTrendingEntity
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.TrendingLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.network.Result
import kotlinx.coroutines.flow.Flow

class TrendingRepository (
    private val localSource: TrendingLocalSource,
    private val remoteSource: MovieRemoteSource
): Repository.TrendingRepos {

    override fun getPopularMovieFromLocalOrRemote() : Flow<Result<List<Movie>>> {
        return resultFlowData(
            localSource = { localSource.getAll().map { it.toMovie() } },
            remoteSource = { remoteSource.getPopularMovie(1) },
            saveData = { body ->
                if (localSource.isNotEmpty()) {
                    body.forEachIndexed { index, movie -> localSource.update(movie.toTrendingEntity(index)) }
                } else {
                    val entity = body.map { it.toTrendingEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getAll().map { it.toMovie() }
            }
        )
    }
}