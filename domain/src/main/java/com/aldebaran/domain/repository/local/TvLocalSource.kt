package com.aldebaran.domain.repository.local

import com.aldebaran.domain.entities.local.TvEntity
import kotlinx.coroutines.flow.Flow

interface TvLocalSource {
    suspend fun insertDiscoverTv(tv: List<TvEntity>)
    suspend fun updateDiscoverTv(tv: TvEntity)
    fun getDiscoverTv(): Flow<List<TvEntity>>
    suspend fun tvRows(): Int
}