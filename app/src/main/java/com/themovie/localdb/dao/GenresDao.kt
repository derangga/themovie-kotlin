package com.themovie.localdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovie.model.db.Genre

@Dao
interface GenresDao {

    @Query("select * from tbl_genre order by name asc limit 4")
    fun getPartOfGenre(): LiveData<List<Genre>>

    @Query("select * from tbl_genre order by name")
    fun getAllGenre(): LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(vararg genre: Genre)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGenre(genre: Genre)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGenre(vararg genre: Genre)
}