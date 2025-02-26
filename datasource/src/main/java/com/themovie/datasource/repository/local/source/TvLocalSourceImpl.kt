package com.themovie.datasource.repository.local.source

import com.themovie.datasource.repository.local.dao.TvDao
import com.themovie.datasource.entities.local.TvEntity
import com.themovie.datasource.repository.local.TvLocalSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TvLocalSourceImpl(
    private val tvDao: TvDao
) : TvLocalSource {

    override suspend fun insertAll(data: List<TvEntity>) {
        withContext(IO) { tvDao.insertAll(*data.toTypedArray()) }
    }

    override suspend fun insert(data: TvEntity) {
        withContext(IO) { tvDao.insert(data) }
    }

    override suspend fun update(data: TvEntity) {
        withContext(IO) { tvDao.update(data) }
    }

    override suspend fun deleteAll() {
        withContext(IO) { tvDao.deleteAllDiscoverTv() }
    }

    override suspend fun delete(data: TvEntity) {
        withContext(IO) { tvDao.delete(data) }
    }

    override suspend fun getAll(): List<TvEntity> {
        return withContext(IO) { tvDao.getDiscoverTv() }
    }

    override fun streamAll(): Flow<List<TvEntity>> {
        return tvDao.streamDiscoverTv()
    }

    override suspend fun isNotEmpty(): Boolean {
        return withContext(IO) { tvDao.countRows() > 0 }
    }
}