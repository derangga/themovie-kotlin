package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TrendingDao
import com.themovie.model.db.Trending
import javax.inject.Inject

class TrendingLocalRepos
    @Inject constructor(private val trendingDao: TrendingDao) {

    suspend fun insert(trending: Trending){
        trendingDao.insertTrending(trending)
    }

    suspend fun insert(trending: List<Trending>){
        trendingDao.insertTrending(*trending.toTypedArray())
    }

    suspend fun update(trending: Trending){
        trendingDao.updateTrendingData(trending)
    }

    suspend fun update(trending: List<Trending>){
        trendingDao.updateTrendingData(*trending.toTypedArray())
    }

    fun getTrendingList(): LiveData<List<Trending>>{
        return trendingDao.getAllTrendingTv()
    }

}