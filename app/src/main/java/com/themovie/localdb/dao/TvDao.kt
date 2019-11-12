package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Tv

@Dao
interface TvDao {
    @Query("select * from tbl_tv")
    fun getAllDiscoverTv(): LiveData<List<Tv>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverTv(tv: Tv)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverTv(vararg tv: Tv)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverTv(tv: Tv)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverTv(vararg tv: Tv)
}