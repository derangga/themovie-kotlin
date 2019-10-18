package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TrendingDao
import com.themovie.model.local.Trending
import javax.inject.Inject

class TrendingLocalRepos
    @Inject constructor(private val trendingDao: TrendingDao) {

    private var trendingListTv: LiveData<List<Trending>> = trendingDao.getAllTrendingTv()

    suspend fun insert(trending: Trending){
        trendingDao.insertTrending(trending)
    }

    suspend fun update(trending: Trending){
        trendingDao.updateTrendingData(trending)
    }

    fun getTrendingList(): LiveData<List<Trending>>{
        return trendingListTv
    }

}