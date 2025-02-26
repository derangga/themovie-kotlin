package com.themovie.datasource.repository.sot

import com.themovie.core.network.Result
import com.themovie.datasource.entities.mapper.toMovie
import com.themovie.datasource.entities.mapper.toTrendingEntity
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.Repository
import com.themovie.datasource.repository.local.TrendingLocalSource
import com.themovie.datasource.repository.remote.MovieRemoteSource
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
                    body.forEachIndexed { index, movie -> localSource.update(movie.toTrendingEntity(index + 1)) }
                } else {
                    val entity = body.map { it.toTrendingEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getAll().map { it.toMovie() }
            }
        )
    }
}