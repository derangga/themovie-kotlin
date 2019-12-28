package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Tv

@Dao
interface TvDao: BaseDao<Tv> {
    @Query("select * from tbl_tv")
    fun getAllDiscoverTv(): LiveData<List<Tv>>

    @Query("select count(*) from tbl_tv")
    suspend fun getSizeOfRows(): Int
}