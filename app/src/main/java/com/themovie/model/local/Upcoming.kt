package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_upcoming")
class Upcoming (@PrimaryKey
                @ColumnInfo(name = "mvId") val mvId: Int,
                @ColumnInfo(name = "title") val title: String,
                @ColumnInfo(name = "date_release") val dateRelease: String,
                @ColumnInfo(name = "poster_path") val posterPath: String?,
                @ColumnInfo(name = "backdrop_path") val backDropPath: String?,
                @ColumnInfo(name = "rating") val rating: String?)