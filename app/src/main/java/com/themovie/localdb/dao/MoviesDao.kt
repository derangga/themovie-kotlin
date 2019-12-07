package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Movies

@Dao
interface MoviesDao: BaseDao<Movies> {
    @Query("select * from tbl_movies")
    fun getAllDiscoverMovies(): LiveData<List<Movies>>

    @Query("select count(*) from tbl_movies")
    suspend fun getSizeOfRows(): Int
}