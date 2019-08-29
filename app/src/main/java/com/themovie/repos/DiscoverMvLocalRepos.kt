package com.themovie.repos

import android.app.Application
import androidx.lifecycle.LiveData
import com.themovie.dao.MoviesDao
import com.themovie.localdb.TheMovieDatabase
import com.themovie.model.local.MoviesLocal
import org.jetbrains.anko.doAsync

class DiscoverMvLocalRepos(application: Application) {
    private var moviesDao: MoviesDao
    private var discoverMovieList: LiveData<List<MoviesLocal>>
    init {
        val theMovieDatabase = TheMovieDatabase.getDatabase(application)
        moviesDao = theMovieDatabase.discoverMovies()
        discoverMovieList = moviesDao.getAllDiscoverMovies()
    }

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