package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao: BaseDao<GenreEntity> {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun streamPartOfGenre(): Flow<List<GenreEntity>>

    @Query("select * from tbl_genre order by name")
    fun streamAllGenre(): Flow<List<GenreEntity>>

    @Query("select * from tbl_genre order by name asc limit 4")
    suspend fun getPartOfGenre(): List<GenreEntity>

    @Query("select * from tbl_genre order by name")
    suspend fun getAllGenre(): List<GenreEntity>

    @Query("delete from tbl_genre")
    suspend fun deleteAllGenre()

    @Query("select count(*) from tbl_genre")
    suspend fun countRows(): Int
}