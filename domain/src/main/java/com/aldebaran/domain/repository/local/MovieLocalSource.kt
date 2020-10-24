package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.local.MovieEntity

interface MovieLocalSource{
    suspend fun insertDiscoverMovie(movies: List<MovieEntity>)
    suspend fun insertDiscoverMovie(movie: MovieEntity)
    suspend fun updateDiscoverMovie(movies: MovieEntity)
    fun streamAllDiscoverMovie(): LiveData<List<MovieEntity>>
    suspend fun getAllDiscoverMovie(): List<MovieEntity>
    suspend fun movieRows(): Int
}