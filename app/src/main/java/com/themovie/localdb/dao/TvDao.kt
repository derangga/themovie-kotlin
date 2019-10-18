package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.themovie.model.local.TvLocal

@Dao
interface TvDao {
    @Query("select * from tbl_tv order by id ASC")
    fun getAllDiscoverTv(): LiveData<List<TvLocal>>

    @Insert
    suspend fun insertDiscoverTv(tv: TvLocal)

    @Update
    suspend fun updateDiscoverTv(tv: TvLocal)
}