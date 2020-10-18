package com.aldebaran.data.local.source

import androidx.lifecycle.LiveData
import com.aldebaran.data.local.dao.UpcomingDao
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.aldebaran.domain.repository.local.UpcomingLocalSource

class UpcomingLocalSourceImpl(
    private val upcomingDao: UpcomingDao
) : UpcomingLocalSource {
    override suspend fun insertUpcoming(upcoming: List<UpcomingEntity>) {
        upcomingDao.insertAll(*upcoming.toTypedArray())
    }

    override suspend fun insertUpcoming(upcoming: UpcomingEntity) {
        upcomingDao.insert(upcoming)
    }

    override suspend fun updateUpcoming(upcoming: UpcomingEntity) {
        upcomingDao.update(upcoming)
    }

    override fun getUpcomingMovie(): LiveData<List<UpcomingEntity>> {
        return upcomingDao.streamUpcomingMovie()
    }

    override suspend fun upcomingRows(): Int {
        return upcomingDao.countRows()
    }
}