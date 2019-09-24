package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.TvDao
import com.themovie.model.local.TvLocal
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class DiscoverTvLocalRepos
    @Inject constructor(private val tvDao: TvDao) {

    private var discoverTvList: LiveData<List<TvLocal>> = tvDao.getAllDiscoverTv()

    fun insert(tv: TvLocal){
        doAsync {
            tvDao.insertDiscoverTv(tv)
        }
    }

    fun update(tv: TvLocal){
        doAsync {
            tvDao.updateDiscoverTv(tv)
        }
    }

    fun getDiscoverTvList(): LiveData<List<TvLocal>>{
        return  discoverTvList
    }
}