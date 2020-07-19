package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.local.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalSource{
    suspend fun insertDiscoverMovie(movies: List<MovieEntity>)
    suspend fun updateDiscoverMovie(movies: MovieEntity)
    fun getAllDiscoverMovie(): Flow<List<MovieEntity>>
    suspend fun movieRows(): Int
}