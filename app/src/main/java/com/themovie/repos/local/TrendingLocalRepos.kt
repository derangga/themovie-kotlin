package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TrendingDao
import com.themovie.model.local.Trending
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class TrendingLocalRepos
    @Inject constructor(private val trendingDao: TrendingDao) {

    private var trendingListTv: LiveData<List<Trending>> = trendingDao.getAllTrendingTv()

    fun insert(trending: Trending){
        doAsync {
            trendingDao.insertTrending(trending)
        }
    }

    fun update(trending: Trending){
        doAsync {
            trendingDao.updateTrendingData(trending)
        }
    }

    fun getTrendingList(): LiveData<List<Trending>>{
        return trendingListTv
    }

}