package com.themovie.datasource.repository.local

import com.themovie.datasource.entities.local.GenreEntity

interface GenreLocalSource : LocalSource<GenreEntity> {
    suspend fun getPartOfGenre(): List<GenreEntity>
}