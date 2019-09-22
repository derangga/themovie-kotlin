package com.themovie.repos.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TrendingDao
import com.themovie.localdb.TheMovieDatabase
import com.themovie.model.local.Trending
import org.jetbrains.anko.doAsync

class TrendingLocalRepos(application: Application) {
    private var trendingDao: TrendingDao
    private var trendingListTv: LiveData<List<Trending>>
    init {
        val theMovieDatabase = TheMovieDatabase.getDatabase(application)
        trendingDao = theMovieDatabase.trendingDao()
        trendingListTv = trendingDao.getAllTrendingTv()
    }

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