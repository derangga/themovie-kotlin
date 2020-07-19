package com.aldebaran.data.local.source

import com.aldebaran.data.local.dao.UpcomingDao
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.aldebaran.domain.repository.local.UpcomingLocalSource
import kotlinx.coroutines.flow.Flow

class UpcomingLocalSourceImpl(
    private val upcomingDao: UpcomingDao
): UpcomingLocalSource {
    override suspend fun insertUpcoming(upcoming: List<UpcomingEntity>) {
        upcomingDao.insertAll(*upcoming.toTypedArray())
    }

    override suspend fun updateUpcoming(upcoming: UpcomingEntity) {
        upcomingDao.update(upcoming)
    }

    override fun getUpcomingMovie(): Flow<List<UpcomingEntity>> {
        return upcomingDao.getUpcomingMovie()
    }

    override suspend fun upcomingRows(): Int {
        return upcomingDao.countRows()
    }
}