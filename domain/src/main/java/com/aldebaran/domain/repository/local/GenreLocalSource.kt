package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.Genre
import com.aldebaran.domain.entities.local.GenreEntity

interface GenreLocalSource {
    suspend fun insertGenre(genre: List<GenreEntity>)
    suspend fun insertGenre(genre: GenreEntity)
    suspend fun updateGenre(genre: GenreEntity)
    fun getPartOfGenre(): LiveData<List<GenreEntity>>
    fun getAllGenre(): LiveData<List<GenreEntity>>
    suspend fun genreRows(): Int
}