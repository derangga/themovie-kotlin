package com.themovie.repos

import android.app.Application
import androidx.lifecycle.LiveData
import com.themovie.dao.TvDao
import com.themovie.localdb.TheMovieDatabase
import com.themovie.model.local.TvLocal
import org.jetbrains.anko.doAsync

class DiscoverTvLocalRepos(application: Application) {
    private var tvDao: TvDao
    private var discoverTvList: LiveData<List<TvLocal>>
    init {
        val theMovieDatabase = TheMovieDatabase.getDatabase(application)
        tvDao = theMovieDatabase.discoverTv()
        discoverTvList = tvDao.getAllDiscoverTv()
    }

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