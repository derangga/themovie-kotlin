package com.aldebaran.data.local.source

import androidx.lifecycle.LiveData
import com.aldebaran.data.local.dao.TvDao
import com.aldebaran.domain.entities.local.TvEntity
import com.aldebaran.domain.repository.local.TvLocalSource

class TvLocalSourceImpl(
    private val tvDao: TvDao
): TvLocalSource {
    override suspend fun insertDiscoverTv(tv: List<TvEntity>) {
        tvDao.insertAll(*tv.toTypedArray())
    }

    override suspend fun insertDiscoverTv(tv: TvEntity) {
        tvDao.insert(tv)
    }

    override suspend fun updateDiscoverTv(tv: TvEntity) {
        tvDao.update(tv)
    }

    override fun getDiscoverTv(): LiveData<List<TvEntity>> {
        return tvDao.getDiscoverTv()
    }

    override suspend fun tvRows(): Int {
        return tvDao.countRows()
    }
}