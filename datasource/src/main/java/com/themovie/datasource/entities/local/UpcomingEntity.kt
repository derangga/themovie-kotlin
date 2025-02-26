package com.themovie.datasource.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_upcoming")
data class UpcomingEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int?,
    @ColumnInfo(name = "movieId") val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?
)