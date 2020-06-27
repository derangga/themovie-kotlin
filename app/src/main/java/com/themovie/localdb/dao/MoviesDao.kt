package com.themovie.localdb.dao

import androidx.room.*
import com.themovie.model.db.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao: BaseDao<Movies> {
    @Query("select * from tbl_movies")
    fun getDiscoverMovies(): Flow<List<Movies>>

    @Query("select count(*) from tbl_movies")
    suspend fun countRows(): Int
}