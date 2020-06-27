package com.themovie.localdb.dao

import androidx.room.*
import com.themovie.model.db.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao: BaseDao<Genre> {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun getPartOfGenre(): Flow<List<Genre>>

    @Query("select * from tbl_genre order by name")
    fun getAllGenre(): Flow<List<Genre>>

    @Query("select count(*) from tbl_genre")
    suspend fun countRows(): Int
}