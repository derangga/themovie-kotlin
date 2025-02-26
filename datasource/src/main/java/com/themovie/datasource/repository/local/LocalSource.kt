package com.themovie.datasource.repository.local

import kotlinx.coroutines.flow.Flow

interface LocalSource<T> {
    suspend fun insertAll(data: List<T>)
    suspend fun insert(data: T)
    suspend fun update(data: T)
    suspend fun deleteAll()
    suspend fun delete(data: T)
    suspend fun getAll(): List<T>
    fun streamAll(): Flow<List<T>>
    suspend fun isNotEmpty(): Boolean
}