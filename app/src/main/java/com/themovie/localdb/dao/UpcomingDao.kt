package com.themovie.localdb.dao

import androidx.room.*
import com.themovie.model.db.Upcoming
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingDao: BaseDao<Upcoming> {
    @Query("select * from tbl_upcoming")
    fun getUpcomingMovie(): Flow<List<Upcoming>>

    @Query("select count(*) from tbl_upcoming")
    suspend fun countRows(): Int
}