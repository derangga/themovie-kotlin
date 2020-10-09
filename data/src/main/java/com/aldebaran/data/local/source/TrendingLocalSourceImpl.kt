package com.aldebaran.data.local.source

import androidx.lifecycle.LiveData
import com.aldebaran.data.local.dao.TrendingDao
import com.aldebaran.domain.entities.local.TrendingEntity
import com.aldebaran.domain.repository.local.TrendingLocalSource

class TrendingLocalSourceImpl (
    private val trendingDao: TrendingDao
): TrendingLocalSource {
    override suspend fun insertTrendMovie(trending: List<TrendingEntity>) {
        trendingDao.insertAll(*trending.toTypedArray())
    }

    override suspend fun insertTrendMovie(trending: TrendingEntity) {
        trendingDao.insert(trending)
    }

    override suspend fun updateTrendingMovie(trending: TrendingEntity) {
        trendingDao.update(trending)
    }

    override fun getTrendMovie(): LiveData<List<TrendingEntity>> {
        return trendingDao.getTrending()
    }

    override suspend fun trendingMovieRows(): Int {
        return trendingDao.countRows()
    }
}