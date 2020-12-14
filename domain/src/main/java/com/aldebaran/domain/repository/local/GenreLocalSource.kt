package com.aldebaran.domain.repository.local

import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.entities.ui.Genre

interface GenreLocalSource : LocalSource<GenreEntity> {
    suspend fun getPartOfGenre(): List<GenreEntity>
}