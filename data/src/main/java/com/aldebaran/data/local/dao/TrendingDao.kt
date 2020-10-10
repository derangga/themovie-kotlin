package com.aldebaran.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldebaran.domain.entities.local.TrendingEntity

@Dao
interface TrendingDao: BaseDao<TrendingEntity> {
    @Query("select * from tbl_trending limit 6")
    fun getTrending(): LiveData<List<TrendingEntity>>

    @Query("select count(*) from tbl_trending")
    suspend fun countRows(): Int
}