package com.themovie.datasource.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.themovie.datasource.entities.local.TvEntity
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