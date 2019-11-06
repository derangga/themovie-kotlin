package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Movies

@Dao
interface MoviesDao {
    @Query("select * from tbl_movies")
    fun getAllDiscoverMovies(): LiveData<List<Movies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverMovies(movies: Movies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverMovies(vararg movies: Movies)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverMovies(movies: Movies)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverMovies(vararg movies: Movies)
}