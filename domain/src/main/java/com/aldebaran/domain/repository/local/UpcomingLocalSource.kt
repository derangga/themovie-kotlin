package com.aldebaran.domain.repository.local

import com.aldebaran.domain.entities.local.UpcomingEntity
import kotlinx.coroutines.flow.Flow

interface UpcomingLocalSource {
    suspend fun insertUpcoming(upcoming: List<UpcomingEntity>)
    suspend fun updateUpcoming(upcoming: UpcomingEntity)
    fun getUpcomingMovie(): Flow<List<UpcomingEntity>>
    suspend fun upcomingRows(): Int
}