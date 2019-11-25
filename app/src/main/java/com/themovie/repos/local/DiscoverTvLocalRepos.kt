package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TvDao
import com.themovie.model.db.Tv
import javax.inject.Inject

class DiscoverTvLocalRepos
    @Inject constructor(private val tvDao: TvDao) {

    suspend fun insert(tv: Tv){
        tvDao.insertDiscoverTv(tv)
    }

    suspend fun insert(tv: List<Tv>){
        tvDao.insertDiscoverTv(*tv.toTypedArray())
    }

    suspend fun update(tv: Tv){
        tvDao.updateDiscoverTv(tv)
    }

    suspend fun update(tv: List<Tv>){
        tvDao.updateDiscoverTv(*tv.toTypedArray())
    }

    fun getDiscoverTvList(): LiveData<List<Tv>> = tvDao.getAllDiscoverTv()
}