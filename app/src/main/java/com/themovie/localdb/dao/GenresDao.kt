package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.local.GenreLocal

@Dao
interface GenresDao {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun getPartOfGenre(): LiveData<List<GenreLocal>>

    @Query("select * from tbl_genre order by name")
    fun getAllGenre(): LiveData<List<GenreLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: GenreLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGenre(genre: GenreLocal)
}