package com.aldebaran.data.local.source

import com.aldebaran.data.local.dao.TrendingDao
import com.aldebaran.domain.entities.local.TrendingEntity
import com.aldebaran.domain.repository.local.TrendingLocalSource
import kotlinx.coroutines.flow.Flow

class TrendingLocalSourceImpl (
    private val trendingDao: TrendingDao
): TrendingLocalSource {
    override suspend fun insertTrendMovie(trending: List<TrendingEntity>) {
        trendingDao.insertAll(*trending.toTypedArray())
    }

    override suspend fun updateTrendingMovie(trending: TrendingEntity) {
        trendingDao.update(trending)
    }

    override fun getTrendMovie(): Flow<List<TrendingEntity>> {
        return trendingDao.getTrending()
    }

    override suspend fun trendingMovieRows(): Int {
        return trendingDao.countRows()
    }
}