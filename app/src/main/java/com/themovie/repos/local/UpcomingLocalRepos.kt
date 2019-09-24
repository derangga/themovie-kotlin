package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.UpcomingDao
import com.themovie.model.local.Upcoming
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class UpcomingLocalRepos
    @Inject constructor(private val upcomingDao: UpcomingDao) {

    private var allUpcomingMovie: LiveData<List<Upcoming>> = upcomingDao.getAllUpcomingMv()

    fun insert(upcoming: Upcoming){
        doAsync {
            upcomingDao.insertUpcoming(upcoming)
        }
    }

    fun update(upcoming: Upcoming){
        doAsync {
            upcomingDao.updateUpcoming(upcoming)
        }
    }

    fun getAllUpcoming(): LiveData<List<Upcoming>>{
        return allUpcomingMovie
    }
}