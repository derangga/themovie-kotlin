package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.UpcomingDao
import com.themovie.model.local.Upcoming
import javax.inject.Inject

class UpcomingLocalRepos
    @Inject constructor(private val upcomingDao: UpcomingDao) {

    private var allUpcomingMovie: LiveData<List<Upcoming>> = upcomingDao.getAllUpcomingMv()

    suspend fun insert(upcoming: Upcoming){
        upcomingDao.insertUpcoming(upcoming)
    }

    suspend fun update(upcoming: Upcoming){
        upcomingDao.updateUpcoming(upcoming)
    }

    fun getAllUpcoming(): LiveData<List<Upcoming>>{
        return allUpcomingMovie
    }
}