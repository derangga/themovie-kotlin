package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.UpcomingDao
import com.themovie.model.db.Upcoming
import javax.inject.Inject

class UpcomingLocalRepos
    @Inject constructor(private val upcomingDao: UpcomingDao) {

    suspend fun insert(upcoming: Upcoming){
        upcomingDao.insertUpcoming(upcoming)
    }

    suspend fun insert(upcoing: List<Upcoming>){
        upcomingDao.insertUpcoming(*upcoing.toTypedArray())
    }

    suspend fun update(upcoming: Upcoming){
        upcomingDao.updateUpcoming(upcoming)
    }

    suspend fun update(upcoming: List<Upcoming>){
        upcomingDao.updateUpcoming(*upcoming.toTypedArray())
    }

    fun getAllUpcoming(): LiveData<List<Upcoming>> = upcomingDao.getAllUpcomingMv()

}