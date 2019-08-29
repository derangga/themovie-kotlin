package com.themovie.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.themovie.model.local.MoviesLocal

@Dao
interface MoviesDao {
    @Query("select * from tbl_movies order by id ASC")
    fun getAllDiscoverMovies(): LiveData<List<MoviesLocal>>

    @Insert
    fun insertDiscoverMovies(movies: MoviesLocal)

    @Update
    fun updateDiscoverMovies(movies: MoviesLocal)
}