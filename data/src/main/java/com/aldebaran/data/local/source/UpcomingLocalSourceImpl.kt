package com.aldebaran.data.local.source

import com.aldebaran.data.local.dao.UpcomingDao
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.aldebaran.domain.repository.local.UpcomingLocalSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UpcomingLocalSourceImpl(
    private val upcomingDao: UpcomingDao
) : UpcomingLocalSource {

    override suspend fun insertAll(data: List<UpcomingEntity>) {
        withContext(IO) { upcomingDao.insertAll(*data.toTypedArray()) }
    }

    override suspend fun insert(data: UpcomingEntity) {
        withContext(IO) { upcomingDao.insert(data) }
    }

    override suspend fun update(data: UpcomingEntity) {
        withContext(IO) { upcomingDao.update(data) }
    }

    override suspend fun deleteAll() {
        withContext(IO) { upcomingDao.deleteAllUpcomingMovie() }
    }

    override suspend fun delete(data: UpcomingEntity) {
        withContext(IO) { upcomingDao.delete(data) }
    }

    override suspend fun getAll(): List<UpcomingEntity> {
        return withContext(IO) { upcomingDao.getUpcomingMovie() }
    }

    override fun streamAll(): Flow<List<UpcomingEntity>> {
        return upcomingDao.streamUpcomingMovie()
    }

    override suspend fun isNotEmpty(): Boolean {
        return withContext(IO) { upcomingDao.countRows() > 0 }
    }
}