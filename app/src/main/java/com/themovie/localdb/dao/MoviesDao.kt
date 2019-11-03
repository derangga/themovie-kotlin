package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.local.MoviesLocal

@Dao
interface MoviesDao {
    @Query("select * from tbl_movies order by id ASC")
    fun getAllDiscoverMovies(): LiveData<List<MoviesLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverMovies(movies: MoviesLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDiscoverMovies(movies: MoviesLocal)
}