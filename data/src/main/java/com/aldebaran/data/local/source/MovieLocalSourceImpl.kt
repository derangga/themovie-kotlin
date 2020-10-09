package com.aldebaran.data.local.source

import androidx.lifecycle.LiveData
import com.aldebaran.data.local.dao.MoviesDao
import com.aldebaran.domain.entities.local.MovieEntity
import com.aldebaran.domain.repository.local.MovieLocalSource
import kotlinx.coroutines.flow.Flow

class MovieLocalSourceImpl(
    private val moviesDao: MoviesDao
): MovieLocalSource {
    override suspend fun insertDiscoverMovie(movies: List<MovieEntity>) {
        moviesDao.insertAll(*movies.toTypedArray())
    }

    override suspend fun insertDiscoverMovie(movie: MovieEntity) {
        moviesDao.insert(movie)
    }

    override suspend fun updateDiscoverMovie(movies: MovieEntity) {
        moviesDao.update(movies)
    }

    override fun getAllDiscoverMovie(): LiveData<List<MovieEntity>> {
        return moviesDao.getDiscoverMovies()
    }

    override suspend fun movieRows(): Int {
        return moviesDao.countRows()
    }

}