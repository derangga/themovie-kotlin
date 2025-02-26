package com.themovie.datasource.repository.sot

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.themovie.core.network.Result
import com.themovie.datasource.repository.remote.source.MoviePagingSource
import com.themovie.datasource.repository.remote.source.SearchMoviePagingSource
import com.themovie.datasource.entities.mapper.toMovie
import com.themovie.datasource.entities.mapper.toMovieEntity
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.Repository
import com.themovie.datasource.repository.local.MovieLocalSource
import com.themovie.datasource.repository.remote.MovieRemoteSource
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class MovieRepository(
    private val localSource: MovieLocalSource,
    private val remoteSource: MovieRemoteSource
) : Repository.MovieRepos {

    private val calendar by lazy { Calendar.getInstance() }
    override fun getDiscoverMovieFromLocalOrRemote(): Flow<Result<List<Movie>>> {
        return resultFlowData(
            localSource = {
                val result = localSource.getAll()
                result.map { it.toMovie() }
            },
            remoteSource = { remoteSource.getDiscoverMovie("", calendar.get(Calendar.YEAR), 1) },
            saveData = { body ->
                if (localSource.isNotEmpty()) {
                    body.forEachIndexed { index, movie -> localSource.update(movie.toMovieEntity(index + 1)) }
                } else {
                    val entity = body.map { it.toMovieEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getAll().map { it.toMovie() }
            }
        )
    }

    override fun getDiscoverMoviePaging(genre: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remoteSource, genre) }
        ).flow
    }

    override fun searchMoovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { SearchMoviePagingSource(remoteSource, query) }
        ).flow
    }
}