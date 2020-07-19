package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.UpcomingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingDao: BaseDao<UpcomingEntity> {
    @Query("select * from tbl_upcoming")
    fun getUpcomingMovie(): Flow<List<UpcomingEntity>>

    @Query("select count(*) from tbl_upcoming")
    suspend fun countRows(): Int
}