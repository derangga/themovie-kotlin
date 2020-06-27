package com.themovie.localdb.dao

import androidx.room.*
import com.themovie.model.db.Tv
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao: BaseDao<Tv> {
    @Query("select * from tbl_tv")
    fun getDiscoverTv(): Flow<List<Tv>>

    @Query("select count(*) from tbl_tv")
    suspend fun countRows(): Int
}