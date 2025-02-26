package com.aldebaran.domain.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tv")
data class TvEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int?,
    @ColumnInfo(name = "tvId") val id: Int?,
    @ColumnInfo(name = "title") val name: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: String?,
    @ColumnInfo(name = "vote_count") val voteCount: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "overview") val overview: String?
)