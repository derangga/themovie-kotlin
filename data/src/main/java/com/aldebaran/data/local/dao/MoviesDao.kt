package com.aldebaran.data.local.dao

import androidx.room.*
import com.aldebaran.domain.entities.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao: BaseDao<MovieEntity> {
    @Query("select * from tbl_movies")
    fun getDiscoverMovies(): Flow<List<MovieEntity>>

    @Query("select count(*) from tbl_movies")
    suspend fun countRows(): Int
}