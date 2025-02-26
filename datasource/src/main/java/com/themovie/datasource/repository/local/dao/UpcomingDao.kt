package com.themovie.datasource.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.themovie.datasource.entities.local.UpcomingEntity
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