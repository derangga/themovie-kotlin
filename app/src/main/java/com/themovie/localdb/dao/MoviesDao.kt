package com.themovie.localdb.dao

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
    suspend fun insertDiscoverMovies(movies: MoviesLocal)

    @Update
    suspend fun updateDiscoverMovies(movies: MoviesLocal)
}