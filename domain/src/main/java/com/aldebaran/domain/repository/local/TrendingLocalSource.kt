package com.aldebaran.domain.repository.local

import com.aldebaran.domain.entities.local.TrendingEntity
import kotlinx.coroutines.flow.Flow

interface TrendingLocalSource {
    suspend fun insertTrendMovie(trending: List<TrendingEntity>)
    suspend fun updateTrendingMovie(trending: TrendingEntity)
    fun getTrendMovie(): Flow<List<TrendingEntity>>
    suspend fun trendingMovieRows(): Int
}