package com.aldebaran.domain.repository.local

import com.aldebaran.domain.entities.local.GenreEntity
import kotlinx.coroutines.flow.Flow

interface GenreLocalSource {
    suspend fun insertGenre(genre: List<GenreEntity>)
    suspend fun updateGenre(genre: GenreEntity)
    fun getPartOfGenre(): Flow<List<GenreEntity>>
    fun getAllGenre(): List<GenreEntity>
    suspend fun genreRows(): Int
}