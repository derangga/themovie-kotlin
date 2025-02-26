package com.themovie.datasource.repository.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: T)

    @Delete
    suspend fun delete(data: T)
}