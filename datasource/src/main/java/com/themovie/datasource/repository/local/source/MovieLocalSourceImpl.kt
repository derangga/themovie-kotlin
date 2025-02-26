package com.themovie.datasource.repository.local.source

import com.themovie.datasource.repository.local.dao.MoviesDao
import com.themovie.datasource.entities.local.MovieEntity
import com.themovie.datasource.repository.local.MovieLocalSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieLocalSourceImpl(
    private val moviesDao: MoviesDao
) : MovieLocalSource {
    override suspend fun insertAll(data: List<MovieEntity>) {
        withContext(IO) { moviesDao.insertAll(*data.toTypedArray()) }
    }

    override suspend fun insert(data: MovieEntity) {
        withContext(IO) { moviesDao.insert(data) }
    }

    override suspend fun update(data: MovieEntity) {
        withContext(IO) { moviesDao.update(data) }
    }

    override suspend fun deleteAll() {
        withContext(IO) { moviesDao.deleteAllMovie() }
    }

    override suspend fun delete(data: MovieEntity) {
        withContext(IO) { moviesDao.delete(data) }
    }

    override suspend fun getAll(): List<MovieEntity> {
        return withContext(IO) { moviesDao.getDiscoverMovies() }
    }

    override fun streamAll(): Flow<List<MovieEntity>> {
        return moviesDao.streamDiscoverMovies()
    }

    override suspend fun isNotEmpty(): Boolean {
        return withContext(IO) { moviesDao.countRows() > 0 }
    }
}