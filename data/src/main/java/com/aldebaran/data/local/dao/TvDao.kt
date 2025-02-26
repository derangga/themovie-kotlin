package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao: BaseDao<TvEntity> {
    @Query("select * from tbl_tv")
    fun streamDiscoverTv(): Flow<List<TvEntity>>

    @Query("select * from tbl_tv")
    suspend fun getDiscoverTv(): List<TvEntity>

    @Query("select count(*) from tbl_tv")
    suspend fun countRows(): Int

    @Query("delete from tbl_tv")
    suspend fun deleteAllDiscoverTv()
}