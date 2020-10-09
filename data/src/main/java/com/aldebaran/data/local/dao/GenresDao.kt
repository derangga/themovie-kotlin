package com.aldebaran.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldebaran.domain.entities.local.GenreEntity

@Dao
interface GenresDao: BaseDao<GenreEntity> {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun getPartOfGenre(): LiveData<List<GenreEntity>>

    @Query("select * from tbl_genre order by name")
    fun getAllGenre(): LiveData<List<GenreEntity>>

    @Query("select count(*) from tbl_genre")
    suspend fun countRows(): Int
}