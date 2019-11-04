package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.local.TvLocal

@Dao
interface TvDao {
    @Query("select * from tbl_tv")
    fun getAllDiscoverTv(): LiveData<List<TvLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverTv(tv: TvLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverTv(vararg tv: TvLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverTv(tv: TvLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverTv(vararg tv: TvLocal)
}