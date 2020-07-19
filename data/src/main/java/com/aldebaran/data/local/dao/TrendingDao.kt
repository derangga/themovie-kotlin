package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.TrendingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDao: BaseDao<TrendingEntity> {
    @Query("select * from tbl_trending limit 6")
    fun getTrending(): Flow<List<TrendingEntity>>

    @Query("select count(*) from tbl_trending")
    suspend fun countRows(): Int
}