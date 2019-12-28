package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Upcoming

@Dao
interface UpcomingDao: BaseDao<Upcoming> {
    @Query("select * from tbl_upcoming")
    fun getAllUpcomingMv(): LiveData<List<Upcoming>>

    @Query("select count(*) from tbl_upcoming")
    suspend fun getSizeOfRows(): Int
}