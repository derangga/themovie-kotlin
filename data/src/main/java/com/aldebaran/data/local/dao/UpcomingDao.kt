package com.aldebaran.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldebaran.domain.entities.local.UpcomingEntity

@Dao
interface UpcomingDao: BaseDao<UpcomingEntity> {
    @Query("select * from tbl_upcoming")
    fun streamUpcomingMovie(): LiveData<List<UpcomingEntity>>

    @Query("select count(*) from tbl_upcoming")
    suspend fun countRows(): Int
}