package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Upcoming

@Dao
interface UpcomingDao {
    @Query("select * from tbl_upcoming")
    fun getAllUpcomingMv(): LiveData<List<Upcoming>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(upcoming: Upcoming)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(vararg upcoming: Upcoming)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUpcoming(upcoming: Upcoming)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUpcoming(vararg upcoming: Upcoming)
}