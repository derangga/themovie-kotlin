package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.themovie.model.local.Upcoming

@Dao
interface UpcomingDao {
    @Query("select * from tbl_upcoming order by id ASC")
    fun getAllUpcomingMv(): LiveData<List<Upcoming>>

    @Insert
    suspend fun insertUpcoming(upcoming: Upcoming)

    @Update
    suspend fun updateUpcoming(upcoming: Upcoming)
}