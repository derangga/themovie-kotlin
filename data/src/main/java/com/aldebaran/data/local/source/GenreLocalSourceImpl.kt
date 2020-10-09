package com.aldebaran.data.local.source

import androidx.lifecycle.LiveData
import com.aldebaran.data.local.dao.GenresDao
import com.aldebaran.domain.entities.Genre
import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.repository.local.GenreLocalSource

class GenreLocalSourceImpl(
    private val genresDao: GenresDao
): GenreLocalSource {
    override suspend fun insertGenre(genre: List<GenreEntity>) {
        genresDao.insertAll(*genre.toTypedArray())
    }

    override suspend fun insertGenre(genre: GenreEntity) {
        genresDao.insert(genre)
    }

    override suspend fun updateGenre(genre: GenreEntity) {
        genresDao.update(genre)
    }

    override fun getPartOfGenre(): LiveData<List<GenreEntity>> {
        return genresDao.getPartOfGenre()
    }

    override fun getAllGenre(): LiveData< List<GenreEntity>> {
        return genresDao.getAllGenre()
    }

    override suspend fun genreRows(): Int {
        return genresDao.countRows()
    }

}