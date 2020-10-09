package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.local.TvEntity

interface TvLocalSource {
    suspend fun insertDiscoverTv(tv: List<TvEntity>)
    suspend fun insertDiscoverTv(tv: TvEntity)
    suspend fun updateDiscoverTv(tv: TvEntity)
    fun getDiscoverTv(): LiveData<List<TvEntity>>
    suspend fun tvRows(): Int
}