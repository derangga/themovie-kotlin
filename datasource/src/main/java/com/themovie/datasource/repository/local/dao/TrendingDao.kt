package com.themovie.datasource.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.themovie.datasource.entities.local.TrendingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDao: BaseDao<TrendingEntity> {
    @Query("select * from tbl_trending limit 8")
    suspend fun getAllTrendingMovie(): List<TrendingEntity>

    @Query("select * from tbl_trending limit 8")
    fun streamTrendingMovie(): Flow<List<TrendingEntity>>

    @Query("select count(*) from tbl_trending")
    suspend fun countRows(): Int

    @Query("delete from tbl_trending")
    suspend fun deleteAllTrendingMovie()
}