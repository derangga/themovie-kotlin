package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.themovie.model.local.Trending

@Dao
interface TrendingDao {
    @Query("select * from tbl_trending order by id asc limit 6")
    fun getAllTrendingTv(): LiveData<List<Trending>>

    @Insert
    fun insertTrending(trending: Trending)

    @Update
    fun updateTrendingData(trending: Trending)
}