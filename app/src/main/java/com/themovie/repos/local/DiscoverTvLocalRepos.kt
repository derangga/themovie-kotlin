package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TvDao
import com.themovie.model.local.TvLocal
import javax.inject.Inject

class DiscoverTvLocalRepos
    @Inject constructor(private val tvDao: TvDao) {

    private var discoverTvList: LiveData<List<TvLocal>> = tvDao.getAllDiscoverTv()

    suspend fun insert(tv: TvLocal){
        tvDao.insertDiscoverTv(tv)
    }

    suspend fun insert(tv: List<TvLocal>){
        tvDao.insertDiscoverTv(*tv.toTypedArray())
    }

    suspend fun update(tv: TvLocal){
        tvDao.updateDiscoverTv(tv)
    }

    suspend fun update(tv: List<TvLocal>){
        tvDao.updateDiscoverTv(*tv.toTypedArray())
    }

    fun getDiscoverTvList(): LiveData<List<TvLocal>>{
        return  discoverTvList
    }
}