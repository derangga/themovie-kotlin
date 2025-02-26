package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.UpcomingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingDao: BaseDao<UpcomingEntity> {
    @Query("select * from tbl_upcoming")
    fun streamUpcomingMovie(): Flow<List<UpcomingEntity>>

    @Query("select * from tbl_upcoming")
    suspend fun getUpcomingMovie(): List<UpcomingEntity>

    @Query("delete from tbl_upcoming")
    suspend fun deleteAllUpcomingMovie()

    @Query("select count(*) from tbl_upcoming")
    suspend fun countRows(): Int
}