package com.themovie.repos.local


import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.MoviesDao
import com.themovie.model.local.MoviesLocal
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class DiscoverMvLocalRepos
    @Inject constructor(private val moviesDao: MoviesDao) {

    private var discoverMovieList: LiveData<List<MoviesLocal>> = moviesDao.getAllDiscoverMovies()

    fun insert(movies: MoviesLocal){
        doAsync {
            moviesDao.insertDiscoverMovies(movies)
        }
    }

    fun update(movies: MoviesLocal){
        doAsync {
            moviesDao.updateDiscoverMovies(movies)
        }
    }

    fun getDiscoverMovieLis(): LiveData<List<MoviesLocal>>{
        return discoverMovieList
    }
}