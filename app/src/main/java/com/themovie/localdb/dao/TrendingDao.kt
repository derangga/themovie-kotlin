package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Trending

@Dao
interface TrendingDao: BaseDao<Trending> {
    @Query("select * from tbl_trending limit 6")
    fun getAllTrendingTv(): LiveData<List<Trending>>

    @Query("select count(*) from tbl_trending")
    suspend fun getSizeOfRows(): Int
}