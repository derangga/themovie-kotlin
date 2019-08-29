package com.themovie.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.themovie.model.local.Trending

@Dao
interface TrendingDao {
    @Query("select * from tbl_trending order by id ASC")
    fun getAllTrendingTv(): LiveData<List<Trending>>

    @Insert
    fun insertTrending(trending: Trending)

    @Update
    fun updateTrendingData(trending: Trending)
}