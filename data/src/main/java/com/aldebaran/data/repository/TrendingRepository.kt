package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.Movie
import com.aldebaran.domain.entities.local.TrendingEntity
import com.aldebaran.domain.entities.toTrendingEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.TrendingLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class TrendingRepository (
    private val local: TrendingLocalSource,
    private val remote: MovieRemoteSource
): Repository.TrendingRepos {

    override fun getPopularMovieFromLocalOrRemote() : LiveData<Result<List<TrendingEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getTrendMovie() },
            networkCall = { remote.getPopularMovie(1) },
            saveCallResult = { res ->
                val rows = local.trendingMovieRows()
                if(rows == 0) {
                    res.results.forEach { local.insertTrendMovie(it.toTrendingEntity()) }
                } else {
                    res.results.forEachIndexed { key, trending ->
                        local.updateTrendingMovie(trending.toTrendingEntity(key + 1))
                    }
                }
            }
        )
    }
}