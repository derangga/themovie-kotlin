package com.themovie.datasource.repository.local.source

import com.themovie.datasource.repository.local.dao.GenresDao
import com.themovie.datasource.entities.local.GenreEntity
import com.themovie.datasource.repository.local.GenreLocalSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GenreLocalSourceImpl(
    private val genresDao: GenresDao
): GenreLocalSource {
    override suspend fun insertAll(data: List<GenreEntity>) {
        withContext(IO) { genresDao.insertAll(*data.toTypedArray()) }
    }

    override suspend fun insert(data: GenreEntity) {
        withContext(IO) { genresDao.insert(data) }
    }

    override suspend fun update(data: GenreEntity) {
        withContext(IO) { genresDao.update(data) }
    }

    override suspend fun deleteAll() {
        withContext(IO) { genresDao.deleteAllGenre() }
    }

    override suspend fun delete(data: GenreEntity) {
        withContext(IO) { genresDao.delete(data) }
    }

    override suspend fun getAll(): List<GenreEntity> {
        return withContext(IO) { genresDao.getAllGenre() }
    }

    override fun streamAll(): Flow<List<GenreEntity>> {
        return genresDao.streamAllGenre()
    }

    override suspend fun isNotEmpty(): Boolean {
        return withContext(IO) { genresDao.countRows() > 0 }
    }

    override suspend fun getPartOfGenre(): List<GenreEntity> {
        return withContext(IO) { genresDao.getPartOfGenre() }
    }
}