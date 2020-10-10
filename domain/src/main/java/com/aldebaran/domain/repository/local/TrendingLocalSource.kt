package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.local.TrendingEntity

interface TrendingLocalSource {
    suspend fun insertTrendMovie(trending: List<TrendingEntity>)
    suspend fun insertTrendMovie(trending: TrendingEntity)
    suspend fun updateTrendingMovie(trending: TrendingEntity)
    fun getTrendMovie(): LiveData<List<TrendingEntity>>
    suspend fun trendingMovieRows(): Int
}