package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aldebaran.data.network.source.MoviePagingSource
import com.aldebaran.data.network.source.SearchMoviePagingSource
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.local.MovieEntity
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.entities.toMovieEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.MovieLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class MovieRepository(
    private val local: MovieLocalSource,
    private val remote: MovieRemoteSource
) : Repository.MovieRepos {

    private val calendar by lazy { Calendar.getInstance() }

    override fun getDIscoverMovieFromLocalOrRemote(): LiveData<Result<List<MovieEntity>>> {
        return resultLiveData(
            databaseQuery = { local.streamAllDiscoverMovie() },
            networkCall = { remote.getDiscoverMovie("", calendar.get(Calendar.YEAR), 1) },
            saveCallResult = { res ->
                val rows = local.movieRows()
                if (rows == 0) {
                    res.results.map { it.toMovieEntity() }
                        .also { local.insertDiscoverMovie(it) }
                } else {
                    res.results.forEachIndexed { key, data ->
                        local.updateDiscoverMovie(data.toMovieEntity(key + 1))
                    }
                }
            })
    }

    override fun getDiscoverMoviePaging(genre: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(remote, genre) }
        ).flow
    }

    override fun searchMoovie(query: String): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { SearchMoviePagingSource(remote, query) }
        ).flow
    }
}