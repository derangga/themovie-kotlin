package com.themovie.localdb.dao

import androidx.room.*
import com.themovie.model.db.Trending
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDao: BaseDao<Trending> {
    @Query("select * from tbl_trending limit 6")
    fun getTrending(): Flow<List<Trending>>

    @Query("select count(*) from tbl_trending")
    suspend fun countRows(): Int
}