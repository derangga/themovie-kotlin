package com.themovie.datasource.repository.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.themovie.datasource.entities.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao: BaseDao<MovieEntity> {
    @Query("select * from tbl_movies")
    fun streamDiscoverMovies(): Flow<List<MovieEntity>>

    @Query("select * from tbl_movies")
    suspend fun getDiscoverMovies(): List<MovieEntity>

    @Query("select count(*) from tbl_movies")
    suspend fun countRows(): Int

    @Query("delete from tbl_movies")
    suspend fun deleteAllMovie()
}