package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.local.Trending

@Dao
interface TrendingDao {
    @Query("select * from tbl_trending limit 6")
    fun getAllTrendingTv(): LiveData<List<Trending>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(trending: Trending)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(vararg trending: Trending)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTrendingData(trending: Trending)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTrendingData(vararg trending: Trending)
}