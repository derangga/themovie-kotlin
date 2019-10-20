package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.local.Upcoming

@Dao
interface UpcomingDao {
    @Query("select * from tbl_upcoming order by id ASC")
    fun getAllUpcomingMv(): LiveData<List<Upcoming>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(upcoming: Upcoming)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUpcoming(upcoming: Upcoming)
}