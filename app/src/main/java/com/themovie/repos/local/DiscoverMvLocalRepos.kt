package com.themovie.repos.local


import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.MoviesDao
import com.themovie.model.db.Movies
import javax.inject.Inject

class DiscoverMvLocalRepos
    @Inject constructor(private val moviesDao: MoviesDao) {

    suspend fun insert(movies: Movies){
        moviesDao.insertDiscoverMovies(movies)
    }

    suspend fun insert(movies: List<Movies>){
        moviesDao.insertDiscoverMovies(*movies.toTypedArray())
    }

    suspend fun update(movies: Movies){
        moviesDao.updateDiscoverMovies(movies)
    }

    suspend fun update(movies: List<Movies>){
        moviesDao.updateDiscoverMovies(*movies.toTypedArray())
    }

    fun getDiscoverMovieLis(): LiveData<List<Movies>> = moviesDao.getAllDiscoverMovies()

}