package com.themovie.repos.local


import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.MoviesDao
import com.themovie.model.local.MoviesLocal
import javax.inject.Inject

class DiscoverMvLocalRepos
    @Inject constructor(private val moviesDao: MoviesDao) {

    suspend fun insert(movies: MoviesLocal){
        moviesDao.insertDiscoverMovies(movies)
    }

    suspend fun insert(movies: List<MoviesLocal>){
        moviesDao.insertDiscoverMovies(*movies.toTypedArray())
    }

    suspend fun update(movies: MoviesLocal){
        moviesDao.updateDiscoverMovies(movies)
    }

    suspend fun update(movies: List<MoviesLocal>){
        moviesDao.updateDiscoverMovies(*movies.toTypedArray())
    }

    fun getDiscoverMovieLis(): LiveData<List<MoviesLocal>>{
        return moviesDao.getAllDiscoverMovies()
    }
}