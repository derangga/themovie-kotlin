package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.Movie
import com.aldebaran.domain.entities.local.MovieEntity
import com.aldebaran.domain.entities.toMovieEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.MovieLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import java.util.*

class MovieRepository(
    private val local: MovieLocalSource,
    private val remote: MovieRemoteSource
): Repository.MovieRepos {

    private val calendar by lazy { Calendar.getInstance() }

    override fun getDIscoverMovieFromLocalOrRemote(): LiveData<Result<List<MovieEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getAllDiscoverMovie() },
            networkCall = { remote.getDiscoverMovie("", calendar.get(Calendar.YEAR), 1) },
            saveCallResult = { res ->
                val rows = local.movieRows()
                if(rows == 0) {
                    res.results.forEach { local.insertDiscoverMovie(it.toMovieEntity()) }
                } else {
                    res.results.forEachIndexed { key, data ->
                        local.updateDiscoverMovie(data.toMovieEntity(key + 1))
                    }

                }
            })
    }
}