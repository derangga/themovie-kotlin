package com.aldebaran.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldebaran.data.network.source.MoviePagingSource
import com.aldebaran.data.network.source.SearchMoviePagingSource
import com.aldebaran.data.resultFlowData
import com.aldebaran.domain.entities.mapper.toMovie
import com.aldebaran.domain.entities.mapper.toMovieEntity
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.MovieLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.network.Result
import kotlinx.coroutines.flow.Flow
import java.util.*

class MovieRepository(
    private val localSource: MovieLocalSource,
    private val remoteSource: MovieRemoteSource
) : Repository.MovieRepos {

    private val calendar by lazy { Calendar.getInstance() }
    override fun getDiscoverMovieFromLocalOrRemote(): Flow<Result<List<Movie>>> {
        return resultFlowData(
            localSource = { localSource.getAll().map { it.toMovie() } },
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