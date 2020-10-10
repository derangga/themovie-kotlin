package com.aldebaran.domain.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldebaran.domain.entities.Movie

@Entity(tableName = "tbl_movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int = 0,
    @ColumnInfo(name = "movieId") override val id: Int?,
    @ColumnInfo(name = "title") override val title: String?,
    @ColumnInfo(name = "poster_path") override val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") override val backdropPath: String?,
    @ColumnInfo(name = "overview") override val overview: String?,
    @ColumnInfo(name = "vote_average") override val voteAverage: String?,
    @ColumnInfo(name = "release_date") override val releaseDate: String?
): Movie