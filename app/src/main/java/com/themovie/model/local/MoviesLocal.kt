package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movies")
class MoviesLocal(@PrimaryKey
                  @ColumnInfo(name = "movieId") val mvId: Int,
                  @ColumnInfo(name = "title") val title: String,
                  @ColumnInfo(name = "date_release") val dateRelease: String,
                  @ColumnInfo(name = "rating") val rating: String,
                  @ColumnInfo(name = "poster_path") val posterPath: String?,
                  @ColumnInfo(name = "backdrop_path") val backDropPath: String?)