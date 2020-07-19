package com.aldebaran.data.local.source

import com.aldebaran.data.local.dao.GenresDao
import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.repository.local.GenreLocalSource
import kotlinx.coroutines.flow.Flow

class GenreLocalSourceImpl(
    private val genresDao: GenresDao
): GenreLocalSource {
    override suspend fun insertGenre(genre: List<GenreEntity>) {
        genresDao.insertAll(*genre.toTypedArray())
    }

    override suspend fun updateGenre(genre: GenreEntity) {
        genresDao.update(genre)
    }

    override fun getPartOfGenre(): Flow<List<GenreEntity>> {
        return genresDao.getPartOfGenre()
    }

    override fun getAllGenre(): List<GenreEntity> {
        return genresDao.getAllGenre()
    }

    override suspend fun genreRows(): Int {
        return genresDao.countRows()
    }

}