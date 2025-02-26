package com.themovie.datasource.repository.local.source

import com.themovie.datasource.repository.local.dao.TrendingDao
import com.themovie.datasource.entities.local.TrendingEntity
import com.themovie.datasource.repository.local.TrendingLocalSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TrendingLocalSourceImpl(
    private val trendingDao: TrendingDao
) : TrendingLocalSource {

    override suspend fun insertAll(data: List<TrendingEntity>) {
        withContext(IO) { trendingDao.insertAll(*data.toTypedArray()) }
    }

    override suspend fun insert(data: TrendingEntity) {
        withContext(IO) { trendingDao.insert(data) }
    }

    override suspend fun update(data: TrendingEntity) {
        withContext(IO) { trendingDao.update(data) }
    }

    override suspend fun deleteAll() {
        withContext(IO) { trendingDao.deleteAllTrendingMovie() }
    }

    override suspend fun delete(data: TrendingEntity) {
        withContext(IO) { trendingDao.delete(data) }
    }

    override suspend fun getAll(): List<TrendingEntity> {
        return withContext(IO) { trendingDao.getAllTrendingMovie() }
    }

    override fun streamAll(): Flow<List<TrendingEntity>> {
        return trendingDao.streamTrendingMovie()
    }

    override suspend fun isNotEmpty(): Boolean {
        return withContext(IO) { trendingDao.countRows() > 0 }
    }
}