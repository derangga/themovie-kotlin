package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Genre

@Dao
interface GenresDao: BaseDao<Genre> {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun getPartOfGenre(): LiveData<List<Genre>>

    @Query("select * from tbl_genre order by name")
    fun getAllGenre(): LiveData<List<Genre>>

    @Query("select count(*) from tbl_genre")
    suspend fun getSizeOfRows(): Int
}