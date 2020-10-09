package com.aldebaran.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldebaran.domain.entities.local.MovieEntity

@Dao
interface MoviesDao: BaseDao<MovieEntity> {
    @Query("select * from tbl_movies")
    fun getDiscoverMovies(): LiveData<List<MovieEntity>>

    @Query("select count(*) from tbl_movies")
    suspend fun countRows(): Int
}