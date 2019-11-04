package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_genre")
class GenreLocal(
    @PrimaryKey
    @ColumnInfo(name = "genreId") val genreId: Int,
    @ColumnInfo(name = "name") val name: String?
)