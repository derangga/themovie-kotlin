package com.aldebaran.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldebaran.domain.entities.local.TvEntity

@Dao
interface TvDao: BaseDao<TvEntity> {
    @Query("select * from tbl_tv")
    fun getDiscoverTv(): LiveData<List<TvEntity>>

    @Query("select count(*) from tbl_tv")
    suspend fun countRows(): Int
}