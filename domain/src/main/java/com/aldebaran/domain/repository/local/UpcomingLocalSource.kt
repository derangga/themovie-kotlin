package com.aldebaran.domain.repository.local

import androidx.lifecycle.LiveData
import com.aldebaran.domain.entities.local.UpcomingEntity

interface UpcomingLocalSource {
    suspend fun insertUpcoming(upcoming: List<UpcomingEntity>)
    suspend fun insertUpcoming(upcoming: UpcomingEntity)
    suspend fun updateUpcoming(upcoming: UpcomingEntity)
    fun getUpcomingMovie(): LiveData<List<UpcomingEntity>>
    suspend fun upcomingRows(): Int
}