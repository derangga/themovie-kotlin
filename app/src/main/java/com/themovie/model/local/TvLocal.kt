package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tv")
class TvLocal(
    @PrimaryKey
    @ColumnInfo(name = "tvId") val tvId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backDropPath: String?
)