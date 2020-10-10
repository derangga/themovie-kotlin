package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.Movie
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.aldebaran.domain.entities.toUpcomingEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.UpcomingLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class UpcomingRepository (
    private val local: UpcomingLocalSource,
    private val remote: MovieRemoteSource
): Repository.UpcomingRepos {

    override fun getUpcomingFromLocalOrRemote(): LiveData<Result<List<UpcomingEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getUpcomingMovie() },
            networkCall = { remote.getUpcomingMovie(1) },
            saveCallResult = { res ->
                val rows = local.upcomingRows()
                if(rows == 0) {
                    res.results.forEach { local.insertUpcoming(it.toUpcomingEntity()) }
                } else {
                    res.results.forEachIndexed { key, upcoming ->
                        local.updateUpcoming(upcoming.toUpcomingEntity(key + 1))
                    }
                }
            }
        )
    }
}